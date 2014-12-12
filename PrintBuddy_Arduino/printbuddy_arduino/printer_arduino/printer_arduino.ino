      
String input;

//Motor Pins
//Pulley System
int leftMotor = 10;
int rightMotor = 11;

//Dobber
int dobberMotor = 7;


int startBit = 0;
int endBit = 23;

int positionPrinter = 0;



void setup() {
  pinMode(rightMotor, OUTPUT);
  pinMode(leftMotor, OUTPUT);
  pinMode(dobberMotor, OUTPUT);
  digitalWrite(dobberMotor, HIGH);
    
  Serial.begin(9600); // Default connection rate for my BT module
    
}
void loop(){
  input = Serial.readStringUntil('\n');
    if(input != ""){
      for(int i = 0; i < 1; i++)
      {
        String passedValue = input.substring(startBit, endBit);
        beginPrintLine(passedValue);
        startBit += 24;
        endBit += 24;
        //Move printbuddy
      }
    }
  }
void beginPrintLine(String input) {
  for(int i = 0; i <= 23; i++){
    positionPrinter++;
    if(input.charAt(i) == '1'){
       dropDobber();
       delay(750);
       moveChassis();
       delay(50);
    }
    else
    {
      delay(1500); 
      moveChassis();
      delay(50);
       
    }
  }
  positionPrinter = 0;
  rewind();
 }


void rewind(){
  digitalWrite(leftMotor, HIGH);
  delay(4000);
  digitalWrite(leftMotor, LOW);
  
}

void dropDobber(){
    digitalWrite(dobberMotor, LOW);
    delay(750);
    digitalWrite(dobberMotor, HIGH);
      
}

void moveChassis() {
  digitalWrite(rightMotor, HIGH);
  
  if(positionPrinter >= 6){
    int timerDelay = (((positionPrinter / 6) + 1) * 100);
    delay(timerDelay);
  }
  else
  {
   delay(150); 
  }
  digitalWrite(rightMotor, LOW);
  delay(300);
}
