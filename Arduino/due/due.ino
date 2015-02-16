int btnRepeticion = 8;
int btnSiguienteP = 9;
int btnExit       = 10;

int tipoDeEjercicio = 1;
int peso[4]       = {
  0,0,0,0};
int pesoN[3]      = {
  0,0,0};
int numeroDePesos =0;

void setup(){
  Serial.begin(9600);
  Serial1.begin(9600);

  pinMode(btnExit,INPUT);
  pinMode(btnRepeticion,INPUT);
  pinMode(btnSiguienteP,INPUT);
}

void loop(){
  delay(500);
  ciclo();
}

void ciclo(){      //Funcion tipo de Ejercicio

    Serial1.write("\nMenu\n");
  hacerTiempo();

  if(Serial.available()){

    tipoDeEjercicio = Serial.read();

    if(tipoDeEjercicio==1){        //Por medio de estos if puedes saber que tipo de ejercicio quiere realizar el usuario. Te pongo estos if por si debes enviar alguna señal a la máquina
      // benchPress
    }
    else if(tipoDeEjercicio==2){
      // shoulderPress
    }
    else if(tipoDeEjercicio==3){
      //rows
    }
    else if(tipoDeEjercicio==4){
      //deadlift
    }
    else if(tipoDeEjercicio==5){
      //squat
    }
    else if(tipoDeEjercicio==6){
      //standingCalf
    }
    else if(tipoDeEjercicio==7){
      //other
    }


    entrenamiento(tipoDeEjercicio); //Manda a la funcion que decide el tipo de entrenamiento

  }
}

void entrenamiento(int tipoDeEntrenamiento){

  hacerTiempo();

  int tipoDeEntrenamientoAux = Serial.read();

  if(tipoDeEntrenamientoAux == 0){

    hacerTiempo();
    numeroDePesos = Serial.read();

    if(tipoDeEntrenamiento==2){
      if(numeroDePesos == 1){
        pesoN[0]=obtenPeso(); 
        pesoN[1]=0; 
        pesoN[2]=0; 
        pesoN[3]=0; 
      }
      else if(numeroDePesos == 2) {
        pesoN[0]=obtenPeso(); 
        pesoN[1]=obtenPeso(); 
        pesoN[2]=0; 
        pesoN[3]=0; 
      }
      else if(numeroDePesos == 3) {
        pesoN[0]=obtenPeso(); 
        pesoN[1]=obtenPeso(); 
        pesoN[2]=obtenPeso(); 
        pesoN[3]=0; 
      }
      else if(numeroDePesos == 4) {
        pesoN[0]=obtenPeso(); 
        pesoN[1]=obtenPeso(); 
        pesoN[2]=obtenPeso(); 
        pesoN[3]=obtenPeso(); 
      }
    }

    if(numeroDePesos == 1){
      peso[0]=obtenPeso(); 
      peso[1]=0; 
      peso[2]=0; 
      peso[3]=0; 
    }
    else if(numeroDePesos == 2) {
      peso[0]=obtenPeso(); 
      peso[1]=obtenPeso(); 
      peso[2]=0; 
      peso[3]=0; 
    }
    else if(numeroDePesos == 3) {
      peso[0]=obtenPeso(); 
      peso[1]=obtenPeso(); 
      peso[2]=obtenPeso(); 
      peso[3]=0; 
    }
    else if(numeroDePesos == 4) {
      peso[0]=obtenPeso(); 
      peso[1]=obtenPeso(); 
      peso[2]=obtenPeso(); 
      peso[3]=obtenPeso(); 
    } 


    if(tipoDeEjercicio == 1){        //Por medio de estos if puedes saber que tipo de ejercicio quiere realizar el usuario. Te pongo estos if por si debes enviar alguna señal a la máquina
      benchPress(tipoDeEntrenamiento);
    }
    else if(tipoDeEjercicio == 2){
      shoulderPress(tipoDeEntrenamiento);
    }
    else if(tipoDeEjercicio==3){
      rows(tipoDeEntrenamiento);
    }
    else if(tipoDeEjercicio==4){
      deadlift(tipoDeEntrenamiento);
    }
    else if(tipoDeEjercicio==5){
      squat(tipoDeEntrenamiento);
    }
    else if(tipoDeEjercicio==6){
      standingCalf(tipoDeEntrenamiento);
    }
    else if(tipoDeEjercicio==7){
      other(tipoDeEntrenamiento);
    }

  }
  else if(tipoDeEntrenamientoAux > 0)
    entrenamiento(tipoDeEntrenamientoAux);   
  else
    ciclo();     

}

//A partir de aqui ya estan disponibles los valores de numeroDePesos y los pesos peso[0],peso[1], peso[2], peso[3] 

void benchPress(int tipoDeEntrenamiento){

  if(tipoDeEntrenamiento==1){
    dropset();
  } 
  else if(tipoDeEntrenamiento==2){
    negativePositive(); 
  }
  else if(tipoDeEntrenamiento==3){
    negative(); 
  }
}

void shoulderPress(int tipoDeEntrenamiento){

  if(tipoDeEntrenamiento==1){
    dropset();
  } 
  else if(tipoDeEntrenamiento==2){
    negativePositive(); 
  }
  else if(tipoDeEntrenamiento==3){
    negative(); 
  }


}

void rows(int tipoDeEntrenamiento){

  if(tipoDeEntrenamiento==1){
    dropset();
  } 
  else if(tipoDeEntrenamiento==2){
    negativePositive(); 
  }
  else if(tipoDeEntrenamiento==3){
    negative(); 
  }


}

void deadlift(int tipoDeEntrenamiento){

  if(tipoDeEntrenamiento==1){
    dropset();
  } 
  else if(tipoDeEntrenamiento==2){
    negativePositive(); 
  }
  else if(tipoDeEntrenamiento==3){
    negative(); 
  }


}

void squat(int tipoDeEntrenamiento){

  if(tipoDeEntrenamiento==1){
    dropset();
  } 
  else if(tipoDeEntrenamiento==2){
    negativePositive(); 
  }
  else if(tipoDeEntrenamiento==3){
    negative(); 
  }


}

void standingCalf(int tipoDeEntrenamiento){

  if(tipoDeEntrenamiento==1){
    dropset();
  } 
  else if(tipoDeEntrenamiento==2){
    negativePositive(); 
  }
  else if(tipoDeEntrenamiento==3){
    negative(); 
  }
}
void other(int tipoDeEntrenamiento){

  if(tipoDeEntrenamiento==1){
    dropset();
  } 
  else if(tipoDeEntrenamiento==2){
    negativePositive(); 
  }
  else if(tipoDeEntrenamiento==3){
    negative(); 
  }  
}

void dropset(){
  int contadorSiguienteP = 1;
  int valorRepeticion    = 0;
  int valorSiguienteP    = 0;
  int valorExit          = 0;
  int pesoActual         = peso[contadorSiguienteP - 1];

  Serial1.write("\nHaciendo dropset\n");

  while(!Serial.available()){

    valorRepeticion=digitalRead(btnRepeticion);
    valorSiguienteP=digitalRead(btnSiguienteP);
    valorExit      =digitalRead(btnExit);

    if(valorExit== 1){
      Serial.write(-1);
      esperaSalida(1);       
    }

    if(valorRepeticion==1){
      Serial.write(2);
      valorRepeticion    = 0;
      delay(500);
    }

    if((valorSiguienteP==1)&&(contadorSiguienteP < numeroDePesos )){
      contadorSiguienteP++;  //incrementa el contador
      pesoActual= peso[contadorSiguienteP - 1]; 
      valorSiguienteP = 0;
      delay(500);
    }
  }
  esperaSalida(1);   
}

void negativePositive(){
  int contadorSiguienteP = 1;
  int valorRepeticion    = 0;
  int valorSiguienteP    = 0;
  int valorExit          = 0;
  int pesoPositivoActual         = peso[contadorSiguienteP - 1];
  int pesoNegativoActual         = peso[contadorSiguienteP - 1];

  Serial1.write("\nHaciendo dropset\n");

  while(!Serial.available()){

    valorRepeticion=digitalRead(btnRepeticion);
    valorSiguienteP=digitalRead(btnSiguienteP);
    valorExit      =digitalRead(btnExit);

    if(valorExit== 1){
      Serial.write(-1);
      esperaSalida(1);       
    }

    if(valorRepeticion==1){
      Serial.write(2);
      valorRepeticion    = 0;
      delay(500);
    }

    if((valorSiguienteP==1)&&(contadorSiguienteP < numeroDePesos )){
      contadorSiguienteP++;  //incrementa el contador
      pesoPositivoActual         = peso[contadorSiguienteP - 1];
      pesoNegativoActual         = peso[contadorSiguienteP - 1];
      valorSiguienteP = 0;
      delay(500);
    }
  }
  esperaSalida(1);   
}

void negative(){
  int contadorSiguienteP = 1;
  int valorRepeticion    = 0;
  int valorSiguienteP    = 0;
  int valorExit          = 0;
  int pesoActual         = peso[contadorSiguienteP - 1];

  Serial1.write("\nHaciendo dropset\n");

  while(!Serial.available()){

    valorRepeticion=digitalRead(btnRepeticion);
    valorSiguienteP=digitalRead(btnSiguienteP);
    valorExit      =digitalRead(btnExit);

    if(valorExit== 1){
      Serial.write(-1);
      esperaSalida(1);       
    }

    if(valorRepeticion==1){
      Serial.write(2);
      valorRepeticion    = 0;
      delay(500);
    }

    if((valorSiguienteP==1)&&(contadorSiguienteP < numeroDePesos )){
      contadorSiguienteP++;  //incrementa el contador
      pesoActual= peso[contadorSiguienteP - 1]; 
      valorSiguienteP = 0;
      delay(500);
    }
  }
  esperaSalida(1);   
}

void esperaSalida(int tipoDeEntrenamiento){

  hacerTiempo();
  int salida = Serial.read();

  if(salida== -1 ){
    Serial1.write("\nBack\n");
    entrenamiento(tipoDeEntrenamiento);
  }

} 


void hacerTiempo(){
  while(!Serial.available()){
  }
}

int obtenPeso(){
  hacerTiempo();
  int pesoFinal   = 0;
  int pesoTemp    = Serial.read();  

  while(pesoTemp!=0){
    pesoFinal += pesoTemp;
    hacerTiempo();        
    pesoTemp   = Serial.read();
  }



  return pesoFinal;  
}


