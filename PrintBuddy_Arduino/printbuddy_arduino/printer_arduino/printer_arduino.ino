      
String input;

//Motor Pins
//Pulley System
int leftMotorOne = 12;
int leftMotorTwo = 11;

int rightMotorOne = 10;
int rightMotorTwo = 9;

//Dobber
int dobberMotorOne = 7;
int dobberMotorTwo = 6;


int startBit = 0;
int endBit = 23;



void setup() {
    
    pinMode(leftMotorOne, OUTPUT);
    pinMode(leftMotorTwo, OUTPUT);
    
    pinMode(rightMotorOne, OUTPUT);
    pinMode(rightMotorTwo, OUTPUT);
    
    pinMode(dobberMotorOne, OUTPUT);
    pinMode(dobberMotorTwo, OUTPUT);
    
    digitalWrite(dobberMotorOne, HIGH);
    
    //digitalWrite(dobberMotorOne, HIGH);
    //digitalWrite(dobberMotorTwo, LOW);
    
    Serial.begin(9600); // Default connection rate for my BT module
    
}
void loop(){
  input = Serial.readStringUntil('\n');
    if(input != ""){
      while(endBit <= 23)
      {
        String passedValue = input.substring(startBit, endBit);
        beginPrintLine(passedValue);
        startBit += 24;
        endBit += 24;
        //Move printbuddy
      }
      startBit = 0;
      endBit = 23;
    }
  }
void beginPrintLine(String input) {
  for(int i = 0; i < input.length(); i++){
    if(input.charAt(i) == '1'){
       
      delay(1000); 
      dropDobber();
       /*dobberMove();
       delay(1000);*/
    }
    else
    {
      //delay(2000); 
      //moveChassis();
       //delay(1000);
    }
  }
  rewind();
}

void rewind(){
  digitalWrite(leftMotorOne, HIGH);
  digitalWrite(leftMotorTwo, LOW);
  delay(2000);
  digitalWrite(leftMotorOne, LOW);
  digitalWrite(leftMotorTwo, LOW);
  delay(1000);
  
}

void dropDobber(){
    digitalWrite(dobberMotorOne, LOW);
    delay(2000);
    digitalWrite(dobberMotorOne, HIGH);
      
}

void dobberMove() {
  digitalWrite(leftMotorOne, LOW);
  digitalWrite(leftMotorTwo, LOW);
  
  digitalWrite(rightMotorOne, HIGH);
  digitalWrite(rightMotorTwo, LOW);
  delay(400);
  digitalWrite(rightMotorOne,LOW);
  digitalWrite(rightMotorTwo,LOW);
}

void moveChassis() {
  digitalWrite(leftMotorOne, LOW);
  digitalWrite(leftMotorTwo, LOW);
  
  digitalWrite(rightMotorOne, HIGH);
  digitalWrite(rightMotorTwo, LOW);
  delay(450);
  digitalWrite(rightMotorOne,LOW);
  digitalWrite(rightMotorTwo,LOW);
}

