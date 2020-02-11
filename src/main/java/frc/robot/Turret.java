package frc.robot;

public class Turret{
    private static double turAng, tarAng, tarDis, turPow;

    public static boolean lockedOn(){
        double diff=turAng/*calc by enc*/-tarAng/*calc by limelight*/;
        if(diff!=0){
            return(false);
        }else{
            return(true);
        }
    }

    public static void turnTur(double wantAng,double curAng){
        double diff=wantAng-curAng;
        //turret motor set speed (diff)
    }
}