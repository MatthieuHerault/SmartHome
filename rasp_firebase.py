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
ref = db.reference('volet_tous')

def handler(signal_received, frame):
    #Récupération du signal de fin
    print('Fin CTRL-C')
    GPIO.cleanup()
    exit(0)

#Fonction de mise à jour des valeurs
def update_values():
    global last_led_value
    led_value = ref.get()
    if led_value == "Ouvrir" and not last_led_value == "Ouvrir" :
        print("Ouvrir : LED on")
        GPIO.output(pin_LED,GPIO.HIGH)
        last_led_value = "Ouvrir"
        #Doit être dans un programme fils
        time.sleep(3)
        #Etat de sécurité
        ref.set("STOP")
    elif led_value == "Fermer" and not last_led_value == "Fermer" :
        print("Fermer : LED off")
        GPIO.output(pin_LED,GPIO.LOW)
        last_led_value = "Fermer"
        #Doit être dans un programme fils
        time.sleep(3)
        #Etat de sécurité
        ref.set("STOP")

if __name__ == '__main__':
    #Signal de fin
    signal(SIGINT, handler)

    #Initialisation
    GPIO.setmode(GPIO.BOARD)
    GPIO.setwarnings(False)

    #Récupérer les données firebase
    #firebase = firebase.FirebaseApplication('https://raspmaison-6691a.firebaseio.com/', None)
    led_value = ref.get()
    print led_value
    last_led_value = 99

    #Broche 17 est une sortie numerique
    pin_LED = 11
    GPIO.setup(pin_LED, GPIO.OUT, initial = GPIO.HIGH)

    print('Début rasp_firebase.py :')
    while True:
        update_values()
        #time.sleep(0.1)
