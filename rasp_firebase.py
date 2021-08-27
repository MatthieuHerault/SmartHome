#!/usr/bin/python
# -*- coding: utf-8 -*-
import RPi.GPIO as GPIO
import time
from signal import signal, SIGINT
from sys import exit
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

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


#Fonction extinction
def handler(signal_received, frame):
    #Récupération du signal de fin
    print('Fin CTRL-C')
    GPIO.cleanup()
    exit(0)


#Fonction de mise à jour des valeurs du volet chambre 1
def update_volet_chambre_1():
    global last_value_volet_chambre_1
    value_volet_chambre_1 = refVoletChambre1.get()
    #Si commande montée
    if value_volet_chambre_1 == "Ouvrir" and not last_value_volet_chambre_1 == "Ouvrir" :
        print("value_volet_chambre_1 : Ouvrir")
        GPIO.output(relais_Descente_Chambre_1,GPIO.LOW)
        time.sleep(0.2)
        GPIO.output(relais_Montée_Chambre_1,GPIO.HIGH)
        last_state_volet_chambre_1 = "Ouvrir"
        #Doit être dans un programme fils
        time.sleep(3)
        #Etat de sécurité
        refVoletChambre1.set("STOP")
        GPIO.output(relais_Montée_Chambre_1,GPIO.LOW)
    #Si commande descente
    elif value_volet_chambre_1 == "Fermer" and not last_value_volet_chambre_1 == "Fermer" :
        print("value_volet_chambre_1 : Fermer")
        GPIO.output(relais_Montée_Chambre_1,GPIO.LOW)
        time.sleep(0.2)
        GPIO.output(relais_Descente_Chambre_1,GPIO.HIGH)
        last_state_volet_chambre_1 = "Fermer"
        #Doit être dans un programme fils
        time.sleep(3)
        #Etat de sécurité
        refVoletChambre1.set("STOP")
        GPIO.output(relais_Descente_Chambre_1,GPIO.LOW)




if __name__ == '__main__':
    #Signal de fin
    signal(SIGINT, handler)
    #Initialisation
    GPIO.setmode(GPIO.BOARD)
    GPIO.setwarnings(False)
    #Initialisation des valeurs
    last_value_volet_chambre_1 = 99
    last_value_volet_chambre_2 = 99
    last_value_volet_chambre_3 = 99
    last_value_volet_cuisine = 99
    last_value_volet_salon_sud = 99
    last_value_volet_salon_ouest = 99
    #Assignation des PIN
    relais_Montee_Chambre_1 = 12        #GPIO18
    relais_Descente_Chambre_1 = 36      #GPIO16
    relais_Montee_Chambre_2 = 11        #GPIO17
    relais_Descente_Chambre_2 = 32      #GPIO12
    relais_Montee_Chambre_3 = 16        #GPIO23
    relais_Descente_Chambre_3 = 31      #GPIO62
    relais_Montee_Cuisine = 15          #GPIO22
    relais_Descente_Cuisine = 33        #GPIO13
    relais_Montee_Salon_Sud = 13        #GPIO27
    relais_Descente_Salon_Sud = 22      #GPIO25
    relais_Montee_Salon_Ouest = 18      #GPIO24
    relais_Descente_Salon_Ouest = 29    #GPIO5
    #Initialisation des PIN
    GPIO.setup(relais_Montee_Chambre_1, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Descente_Chambre_1, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Montee_Chambre_2, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Descente_Chambre_2, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Montee_Chambre_3, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Descente_Chambre_3, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Montee_Cuisine, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Descente_Cuisine, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Montee_Salon_Sud, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Descente_Salon_Sud, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Montee_Salon_Ouest, GPIO.OUT, initial = GPIO.LOW)
    GPIO.setup(relais_Descente_Salon_Ouest, GPIO.OUT, initial = GPIO.LOW)
    print('Début rasp_firebase.py :')
    while True:
        update_volet_chambre_1()
        #time.sleep(0.1)
