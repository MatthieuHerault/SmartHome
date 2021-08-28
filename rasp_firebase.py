#!/usr/bin/python
# -*- coding: utf-8 -*-
import RPi.GPIO as GPIO
import time
import threading
from signal import signal, SIGINT
from sys import exit
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

#Constantes
etat_decommute = GPIO.HIGH
etat_commute = GPIO.LOW
temps_fin_course = 10

#Assignation des PIN
relais_montee_chambre_1 = 12        #GPIO18
relais_descente_chambre_1 = 36      #GPIO16
relais_montee_chambre_2 = 11        #GPIO17
relais_descente_chambre_2 = 32      #GPIO12
relais_montee_chambre_3 = 16        #GPIO23
relais_descente_chambre_3 = 31      #GPIO62
relais_montee_cuisine = 15          #GPIO22
relais_descente_cuisine = 33        #GPIO13
relais_montee_salon_sud = 13        #GPIO27
relais_descente_salon_sud = 22      #GPIO25
relais_montee_salon_ouest = 18      #GPIO24
relais_descente_salon_ouest = 29    #GPIO5

#Initialisation des valeurs
last_value_volet_chambre_1 = 99
last_value_volet_chambre_2 = 99
last_value_volet_chambre_3 = 99
last_value_volet_cuisine = 99
last_value_volet_salon_sud = 99
last_value_volet_salon_ouest = 99
last_value_volet_tous = 99

#Firebase authentication
cred = credentials.Certificate("/home/pi/python-fire/raspmaison-6691a-firebase-adminsdk-i418e-0ee7e893db.json")
firebase_admin.initialize_app(cred, {
    'databaseURL': "https://raspmaison-6691a.firebaseio.com/"
})

#References des volets
refVoletChambre1 = db.reference('volet_chambre_1')
refVoletChambre2 = db.reference('volet_chambre_2')
refVoletChambre3 = db.reference('volet_chambre_3')
refVoletCuisine = db.reference('volet_cuisine')
refVoletSalonSud = db.reference('volet_salon_sud')
refVoletSalonOuest = db.reference('volet_salon_ouest')
refVoletTous = db.reference('volet_tous')

#Fonction sécurité fin de course
class ThreadFinDeCourse(threading.Thread):
    def __init__(self, temps_fin_course, refVolet, printRefVolet, refRelais, action):
        threading.Thread.__init__(self)
        self.temps_second = temps_fin_course
        self.refVolet = refVolet
        self.printRefVolet = printRefVolet
        self.refRelais = refRelais
        self.action = action
    def run(self):
        #Attendre fin de course
        time.sleep(self.temps_second)
        #Etat de sécurité
        self.refVolet.set("Stop")
        print(self.printRefVolet + " : Fin " +self.action)
        GPIO.output(self.refRelais,etat_decommute)

#Fonction extinction
def handler(signal_received, frame):
    #Récupération du signal de fin
    print('Fin CTRL-C')
    GPIO.cleanup()
    exit(0)

#Fonction de mise à jour des valeurs du volet Chambre 1
def update_volet_chambre_1():
    global last_value_volet_chambre_1
    global relais_montee_chambre_1
    global relais_descente_chambre_1
    value_volet_chambre_1 = refVoletChambre1.get()
    #Si commande montée
    if value_volet_chambre_1 == "Ouvrir" and not last_value_volet_chambre_1 == "Ouvrir" :
        print("refVoletChambre1 : Ouvrir")
        GPIO.output(relais_descente_chambre_1,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_montee_chambre_1,etat_commute)
        last_value_volet_chambre_1 = "Ouvrir"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletChambre1, "refVoletChambre1", relais_montee_chambre_1, "Montée")
        thread.start()
    #Si commande descente
    if value_volet_chambre_1 == "Fermer" and not last_value_volet_chambre_1 == "Fermer" :
        print("refVoletChambre1 : Fermer")
        GPIO.output(relais_montee_chambre_1,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_descente_chambre_1,etat_commute)
        last_value_volet_chambre_1 = "Fermer"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletChambre1, "refVoletChambre1", relais_descente_chambre_1, "Descente")
        thread.start()
    #Si commande stop
    if value_volet_chambre_1 == "Stop" and not last_value_volet_chambre_1 == "Stop" :
        print("refVoletChambre1 : Stop")
        GPIO.output(relais_montee_chambre_1,etat_decommute)
        GPIO.output(relais_descente_chambre_1,etat_decommute)
        last_value_volet_chambre_1 = "Stop"

#Fonction de mise à jour des valeurs du volet Chambre 2
def update_volet_chambre_2():
    global last_value_volet_chambre_2
    value_volet_chambre_2 = refVoletChambre2.get()
    #Si commande montée
    if value_volet_chambre_2 == "Ouvrir" and not last_value_volet_chambre_2 == "Ouvrir" :
        print("refVoletChambre2 : Ouvrir")
        GPIO.output(relais_descente_chambre_2,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_montee_chambre_2,etat_commute)
        last_value_volet_chambre_2 = "Ouvrir"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletChambre2, "refVoletChambre2", relais_montee_chambre_2, "Montée")
        thread.start()
    #Si commande descente
    if value_volet_chambre_2 == "Fermer" and not last_value_volet_chambre_2 == "Fermer" :
        print("refVoletChambre2 : Fermer")
        GPIO.output(relais_montee_chambre_2,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_descente_chambre_2,etat_commute)
        last_value_volet_chambre_2 = "Fermer"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletChambre2, "refVoletChambre2", relais_descente_chambre_2, "Descente")
        thread.start()
    #Si commande stop
    if value_volet_chambre_2 == "Stop" and not last_value_volet_chambre_2 == "Stop" :
        print("refVoletChambre2 : Stop")
        GPIO.output(relais_montee_chambre_2,etat_decommute)
        GPIO.output(relais_descente_chambre_2,etat_decommute)
        last_value_volet_chambre_2 = "Stop"

#Fonction de mise à jour des valeurs du volet Chambre 3
def update_volet_chambre_3():
    global last_value_volet_chambre_3
    value_volet_chambre_3 = refVoletChambre3.get()
    #Si commande montée
    if value_volet_chambre_3 == "Ouvrir" and not last_value_volet_chambre_3 == "Ouvrir" :
        print("refVoletChambre3 : Ouvrir")
        GPIO.output(relais_descente_chambre_3,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_montee_chambre_3,etat_commute)
        last_value_volet_chambre_3 = "Ouvrir"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletChambre3, "refVoletChambre3", relais_montee_chambre_3, "Montée")
        thread.start()
    #Si commande descente
    if value_volet_chambre_3 == "Fermer" and not last_value_volet_chambre_3 == "Fermer" :
        print("refVoletChambre3 : Fermer")
        GPIO.output(relais_montee_chambre_3,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_descente_chambre_3,etat_commute)
        last_value_volet_chambre_3 = "Fermer"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletChambre3, "refVoletChambre3", relais_descente_chambre_3, "Descente")
        thread.start()
    #Si commande stop
    if value_volet_chambre_3 == "Stop" and not last_value_volet_chambre_3 == "Stop" :
        print("refVoletChambre3 : Stop")
        GPIO.output(relais_montee_chambre_3,etat_decommute)
        GPIO.output(relais_descente_chambre_3,etat_decommute)
        last_value_volet_chambre_3 = "Stop"

#Fonction de mise à jour des valeurs du volet cuisine
def update_volet_cuisine():
    global last_value_volet_cuisine
    value_volet_cuisine = refVoletCuisine.get()
    #Si commande montée
    if value_volet_cuisine == "Ouvrir" and not last_value_volet_cuisine == "Ouvrir" :
        print("refVoletCuisine : Ouvrir")
        GPIO.output(relais_descente_cuisine,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_montee_cuisine,etat_commute)
        last_value_volet_cuisine = "Ouvrir"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletCuisine, "refVoletCuisine", relais_montee_cuisine, "Montée")
        thread.start()
    #Si commande descente
    if value_volet_cuisine == "Fermer" and not last_value_volet_cuisine == "Fermer" :
        print("refVoletCuisine : Fermer")
        GPIO.output(relais_montee_cuisine,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_descente_cuisine,etat_commute)
        last_value_volet_cuisine = "Fermer"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletCuisine, "refVoletCuisine", relais_descente_cuisine, "Descente")
        thread.start()
    #Si commande stop
    if value_volet_cuisine == "Stop" and not last_value_volet_cuisine == "Stop" :
        print("refVoletCuisine : Stop")
        GPIO.output(relais_montee_cuisine,etat_decommute)
        GPIO.output(relais_descente_cuisine,etat_decommute)
        last_value_volet_cuisine = "Stop"

#Fonction de mise à jour des valeurs du volet salon sud
def update_volet_salon_sud():
    global last_value_volet_salon_sud
    value_volet_salon_sud = refVoletSalonSud.get()
    #Si commande montée
    if value_volet_salon_sud == "Ouvrir" and not last_value_volet_salon_sud == "Ouvrir" :
        print("refVoletSalonSud : Ouvrir")
        GPIO.output(relais_descente_salon_sud,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_montee_salon_sud,etat_commute)
        last_value_volet_salon_sud = "Ouvrir"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletSalonSud, "refVoletSalonSud", relais_montee_salon_sud, "Montée")
        thread.start()
    #Si commande descente
    if value_volet_salon_sud == "Fermer" and not last_value_volet_salon_sud == "Fermer" :
        print("refVoletSalonSud : Fermer")
        GPIO.output(relais_montee_salon_sud,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_descente_salon_sud,etat_commute)
        last_value_volet_salon_sud = "Fermer"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletSalonSud, "refVoletSalonSud", relais_descente_salon_sud, "Descente")
        thread.start()
    #Si commande stop
    if value_volet_salon_sud == "Stop" and not last_value_volet_salon_sud == "Stop" :
        print("refVoletSalonSud : Stop")
        GPIO.output(relais_montee_salon_sud,etat_decommute)
        GPIO.output(relais_descente_salon_sud,etat_decommute)
        last_value_volet_salon_sud = "Stop"

#Fonction de mise à jour des valeurs du volet salon ouest
def update_volet_salon_ouest():
    global last_value_volet_salon_ouest
    value_volet_salon_ouest = refVoletSalonOuest.get()
    #Si commande montée
    if value_volet_salon_ouest == "Ouvrir" and not last_value_volet_salon_ouest == "Ouvrir" :
        print("refVoletSalonOuest : Ouvrir")
        GPIO.output(relais_descente_salon_ouest,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_montee_salon_ouest,etat_commute)
        last_value_volet_salon_ouest = "Ouvrir"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletSalonOuest, "refVoletSalonOuest", relais_montee_salon_ouest, "Montée")
        thread.start()
    #Si commande descente
    if value_volet_salon_ouest == "Fermer" and not last_value_volet_salon_ouest == "Fermer" :
        print("refVoletSalonOuest : Fermer")
        GPIO.output(relais_montee_salon_ouest,etat_decommute)
        time.sleep(0.2)
        GPIO.output(relais_descente_salon_ouest,etat_commute)
        last_value_volet_salon_ouest = "Fermer"
        #Exécution thread
        thread = ThreadFinDeCourse(temps_fin_course, refVoletSalonOuest, "refVoletSalonOuest", relais_descente_salon_ouest, "Descente")
        thread.start()
    #Si commande stop
    if value_volet_salon_ouest == "Stop" and not last_value_volet_salon_ouest == "Stop" :
        print("refVoletSalonOuest : Stop")
        GPIO.output(relais_montee_salon_ouest,etat_decommute)
        GPIO.output(relais_descente_salon_ouest,etat_decommute)
        last_value_volet_salon_ouest = "Stop"

#Fonction de mise à jour des valeurs tous
def update_volet_salon_tous():
    global last_value_volet_tous
    value_volet_tous = refVoletTous.get()
    relaisMontee = [relais_montee_salon_sud, relais_montee_salon_ouest, relais_montee_cuisine, relais_montee_chambre_1, relais_montee_chambre_2, relais_montee_chambre_3]
    relaisDescente = [relais_descente_salon_sud, relais_descente_salon_ouest, relais_descente_cuisine, relais_descente_chambre_1, relais_descente_chambre_2, relais_descente_chambre_3]
    #Si commande montée
    if value_volet_tous == "Ouvrir" and not last_value_volet_tous == "Ouvrir" :
        print("refVoletTous : Ouvrir")
        for i in range (6) :
            GPIO.output(relaisDescente[i],etat_decommute)
            time.sleep(0.2)
            GPIO.output(relaisMontee[i],etat_commute)
            last_value_volet_tous = "Ouvrir"
            #Exécution thread
            thread = ThreadFinDeCourse(temps_fin_course, refVoletTous, "refVoletTous", relaisMontee[i], "Montée")
            thread.start()
    #Si commande descente
    if value_volet_tous == "Fermer" and not last_value_volet_tous == "Fermer" :
        print("refVoletTous : Fermer")
        for i in range (6) :
            GPIO.output(relaisMontee[i],etat_decommute)
            time.sleep(0.2)
            GPIO.output(relaisDescente[i],etat_commute)
            last_value_volet_tous = "Fermer"
            #Exécution thread
            thread = ThreadFinDeCourse(temps_fin_course, refVoletTous, "refVoletTous", relaisDescente[i], "Descente")
            thread.start()
    #Si commande stop
    if value_volet_tous == "Stop" and not last_value_volet_tous == "Stop" :
        print("refVoletTous : Stop")
        for i in range (6) :
            GPIO.output(relaisMontee[i],etat_decommute)
            GPIO.output(relaisDescente[i],etat_decommute)
        last_value_volet_tous = "Stop"

if __name__ == '__main__':
    #Signal de fin
    signal(SIGINT, handler)
    #Initialisation
    GPIO.setmode(GPIO.BOARD)
    GPIO.setwarnings(False)
    GPIO.cleanup()

    #Initialisation des PIN
    GPIO.setup(relais_montee_chambre_1, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_descente_chambre_1, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_montee_chambre_2, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_descente_chambre_2, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_montee_chambre_3, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_descente_chambre_3, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_montee_cuisine, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_descente_cuisine, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_montee_salon_sud, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_descente_salon_sud, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_montee_salon_ouest, GPIO.OUT, initial = etat_decommute)
    GPIO.setup(relais_descente_salon_ouest, GPIO.OUT, initial = etat_decommute)
    print('Début rasp_firebase.py :')
    while True:
        update_volet_chambre_1()
        update_volet_chambre_2()
        update_volet_chambre_3()
        update_volet_cuisine()
        update_volet_salon_sud()
        update_volet_salon_ouest()
        update_volet_salon_tous()
        #print(last_value_volet_chambre_1)
        #time.sleep(0.5)


#TODO : catcher les erreurs pour décommuter les broches
