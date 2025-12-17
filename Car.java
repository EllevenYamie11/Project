interface  Car{
    String name;
    String model;
    int year;
    int speed;

    public Car(String name, String model,int year){
        this.name = name;
        this.model = model;
        this.year = year;
    }
    void starts(){
        println("Engine has started, Your car is ready to go!");
    }
    double speedAlert();

    class MYCAR implements Car{

        double speedAlert(double speed){
                
                return speed;
            }
         public static void main(String[] args){
            
         }   
    }
    

}