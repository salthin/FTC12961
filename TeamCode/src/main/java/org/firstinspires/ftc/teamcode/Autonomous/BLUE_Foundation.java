//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.FPS.Drivetrain;
import org.firstinspires.ftc.teamcode.FPS.Measurement;
import org.firstinspires.ftc.teamcode.FPS.Vision;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="BLUE_Foundation", group="Tourny")
//@Disabled
public class BLUE_Foundation extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    Drivetrain robot = new Drivetrain();
    private VuforiaLocalizer vuforia = null;

    public BNO055IMU revIMU;
    private ElapsedTime runtime = new ElapsedTime();
    Vision portal = new Vision();
    Measurement sensorSuite = null;

    double margin = .5;

    @Override
    public void runOpMode() {
        robot.map(hardwareMap);
        //robot = new Movement(this);
        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);

        waitForStart(); /** START THE PROGRAM */
        runtime.reset();
//        robot.resetEncoders();


        robot.leftHook.setPosition(.4);
        robot.rightHook.setPosition(.6);
        sleep(1200);

        robot.reverse(5000);
        sleep(300);

        robot.strafeRight(9200, telemetry);
        sleep(300);

        robot.reverse(4500);
        sleep(300);

        robot.leftHook.setPosition(1);
        robot.rightHook.setPosition(0); //grab
        sleep(1200);

        robot.forward(3000);
        sleep(1000);

        robot.rotate(90, telemetry);
        sleep(300);

        robot.reverse(2000);
        sleep(1000);

        robot.leftHook.setPosition(.4);
        robot.rightHook.setPosition(.6); // let go
        sleep(1200);



        robot.forward(11000, telemetry);
        sleep(3000);
//        robot.strafeLeft(1000, telemetry);
//        sleep(3000);
//
//        while (!isStopRequested()) {
//            telemetry.addData("Angle 1 =", robot.sensorSuite.getAngle().angle1);
//            telemetry.addData("Angle 2 =", robot.sensorSuite.getAngle().angle2);
//            telemetry.addData("Angle 3 =", robot.sensorSuite.getAngle().angle3);
//            telemetry.update();
//        }

    }


    public void rotate(float degrees){

        boolean turning = true;

        float targetAngle = sensorSuite.getAngle().angle1 + degrees;
        double ratio;
        double powerPolarity = degrees/Math.abs(degrees);
        double powerMultiplier;

        while(turning && !isStopRequested()){

            ratio = sensorSuite.getAngle().angle1/targetAngle;

            powerMultiplier = 1-ratio;

            if(Math.abs(powerMultiplier) < .15){
                powerMultiplier = .2;
            }
            if(Math.abs(powerMultiplier) > .85){
                powerMultiplier = .85;
            }

            robot.leftBack.setPower(-.2 * powerMultiplier * powerPolarity);
            robot.leftFront.setPower(-.2 * powerMultiplier * powerPolarity);
            robot.rightBack.setPower(.2 * powerMultiplier * powerPolarity);
            robot.rightFront.setPower(.2 * powerMultiplier * powerPolarity);

            if(ratio > .95) turning = false;

        }
        robot.leftBack.setPower(0);
        robot.leftFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.rightFront.setPower(0);
    }
    public void setFourbarPos(double pos){
        robot.fourbarRight.setPosition(pos);
        robot.fourbarLeft.setPosition(1-pos);
    }
    public void grabbersDown(){
        robot.leftHook.setPosition(.4);
        robot.rightHook.setPosition(.6);
    }

    public void succ(ElapsedTime time){
        double starttime = time.milliseconds();
        robot.intakeRight.setPower(-.6);
        robot.intakeLeft.setPower(.6);
        robot.leftFront.setPower(.15);
        robot.leftBack.setPower(.15);
        robot.rightFront.setPower(.15);
        robot.rightBack.setPower(.12);
        // loop until we detect a block or x seconds have expired

        while(robot.blockToggle.getValue() < 1) {

        }

        robot.setPowerAll(0);

    }
}
