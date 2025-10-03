package org.firstinspires.ftc.teamcode.action;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

/** This is an interface for this year's mecanum drive wheels. */
public class mecanumDrive {
    // CONSTRUCT
    static final DecimalFormat df = new DecimalFormat("0.00"); // for rounding
    // DECLARE NULL
    DcMotor frontRightDrive;
    DcMotor frontLeftDrive;
    DcMotor backRightDrive;
    DcMotor backLeftDrive;
    double frontRightPower;
    double frontLeftPower;
    double backRightPower;
    double backLeftPower;
    Telemetry telemetry;
    // DECLARE CUSTOM
    static double totalSpeed = 0.75; //This is to control the percent of energy being applied to the motors.
    double slowSpeed = 0.50; // x% of whatever speed totalSpeed is

    // METHODS
    /** Initializes the mecanum drive wheels.
     * @param opMode If you are constructing from an Auto or TeleOp, type in "this" without the quotation marks.
     */
    public void init(@NonNull OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        frontRightDrive = hardwareMap.get(DcMotor.class, "Front Right");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "Front Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "Back Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "Back Left");

        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Sets the mode of the motors to run WITHOUT encoders
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /** Enable or disable slow mode for the wheels.
     * @param enable True if you want to enable slow-mode. False if not.
     */
    public void slowMode(Boolean enable) {
        slowSpeed = enable ? 1.00 : 0.50;
    }

    /** Changes the maximum speed of the robot.
     * @apiNote By default this is 0.75
     */
    public void setMaxSpeed(double speed) {
        totalSpeed = speed;
    }

    /** Switches the wheels to run using encoders. */
    public void runUsingEncoder() {
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /** Switches the wheels to run without encoders. */
    public void runWithoutEncoder() {
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Switches the wheels to run to position mode. */
    public void runToPosition() {
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /** Sends power to the mecanum wheels.
     * @param x Power to use for strafing. Passing in a gamepad joystick is best.
     * @param y Power to use for forwards/backwards movement. Passing in a gamepad joystick is best.
     * @param rot Power to use for rotating. Passing in a gamepad joystick is best.
     */
    public void setPower(double x, double y, double rot) {//rot is short for rotation
        x = x * 1.1;

        //Code to calculate motor power
        double ratio = Math.max((Math.abs(x) + Math.abs(y) + Math.abs(rot)), 1);
        frontRightPower = (-x - y - rot) / ratio;
        frontLeftPower = (x - y + rot) / ratio;
        backRightPower = (x - y - rot) / ratio;
        backLeftPower = (-x - y + rot) / ratio;

        frontRightDrive.setPower(frontRightPower * totalSpeed * slowSpeed);
        frontLeftDrive.setPower(frontLeftPower * totalSpeed * slowSpeed);
        backRightDrive.setPower(backRightPower * totalSpeed * slowSpeed);
        backLeftDrive.setPower(backLeftPower * totalSpeed * slowSpeed);
    }

    public void telemetryOutput() {
        // Power output
        telemetry.addData("fRMotorPwr", df.format(frontRightPower));
        telemetry.addData("fLMotorPwr", df.format(frontLeftPower));
        telemetry.addData("bRMotorPwr", df.format(backRightPower));
        telemetry.addData("bLMotorPwr", df.format(backLeftPower));
        // Ticks
        telemetry.addData("Front Left Ticks", frontLeftDrive.getCurrentPosition());
        telemetry.addData("Front Right Ticks", frontRightDrive.getCurrentPosition());
        telemetry.addData("Back Left Ticks", backLeftDrive.getCurrentPosition());
        telemetry.addData("Back Right Ticks", backRightDrive.getCurrentPosition());
    }
}
