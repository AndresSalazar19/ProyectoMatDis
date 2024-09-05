#include <SoftwareSerial.h>

SoftwareSerial BT(10, 11); // RX, TX
int pins[10] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11}; // Pines digitales asociados a e1, e2, ..., e10
int lastState[10]; // Para almacenar el estado anterior de cada pin
int switchPin = 12; // Pin donde está conectado el switch
int lastSwitchState = LOW; // Estado previo del switch

void setup() {
  BT.begin(9600);
  Serial.begin(9600);

  for (int i = 0; i < 10; i++) {
    pinMode(pins[i], INPUT);
    lastState[i] = digitalRead(pins[i]); // Inicializa el estado previo
  }
  
  pinMode(switchPin, INPUT);
  lastSwitchState = digitalRead(switchPin); // Inicializa el estado del switch

  Serial.println("Bluetooth iniciado, monitoreando pines y switch...");
}

void loop() {
  // Verificar el estado del switch
  int currentSwitchState = digitalRead(switchPin);

  if (currentSwitchState != lastSwitchState) {
    lastSwitchState = currentSwitchState;

    if (currentSwitchState == HIGH) {
      BT.println("SWITCH ON");
      Serial.println("Switch encendido");
    } else {
      BT.println("SWITCH OFF");
      Serial.println("Switch apagado");
    }
  }

  // Verificar los pines de las aristas
  for (int i = 0; i < 10; i++) {
    int currentState = digitalRead(pins[i]);

    if (currentState != lastState[i]) {
      lastState[i] = currentState;

      if (currentSwitchState == HIGH) {
        if (currentState == HIGH) {
          BT.print("e");
          BT.print(i + 1);
          BT.println("ON"); // Envia e1ON, e2ON, ..., e10ON
        } else {
          BT.print("e");
          BT.print(i + 1);
          BT.println("OFF"); // Envia e1OFF, e2OFF, ..., e10OFF
        }
      } else {
        if (currentState == HIGH) {
          BT.print("e");
          BT.print(i + 1);
          BT.println("RESET_ON"); // Envia e1RESET_ON, e2RESET_ON, ..., e10RESET_ON
        } else {
          BT.print("e");
          BT.print(i + 1);
          BT.println("RESET_OFF"); // Envia e1RESET_OFF, e2RESET_OFF, ..., e10RESET_OFF
        }
      }
    }
  }
  
  delay(100); // Evita la saturación del puerto serie
}
