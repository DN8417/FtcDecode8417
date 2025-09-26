package org.firstinspires.ftc.teamcode.action;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class mecanumDrive {

    // The Variables we need to declare
    double frontRightPower;
    double frontLeftPower;
    double backRightPower;
    double backLeftPower;
    Telemetry telemetry;
    DcMotor frontRightDrive;
    DcMotor frontLeftDrive;
    DcMotor backRightDrive;
    DcMotor backLeftDrive;

    public static double rotationSpeed = 0.75; // this sets the rotation speed as a fraction

    public void init(@NonNull OpMode opMode) {

        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        // this sets the motors to the hardwareMap
        frontRightDrive = hardwareMap.get(DcMotor.class, "Front Right");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "Front Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "Back Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "Back Left");

    }

    public void drive(double movingJoystick, double rotatingJoystick) {

        // This calculates the power the motors run at when moving and rotating at the same time
        frontRightPower = Range.clip(movingJoystick + (rotatingJoystick * rotationSpeed), -1.0, 1.0);
        frontLeftPower = Range.clip(movingJoystick - (rotatingJoystick * rotationSpeed), -1.0, 1.0);
        backRightPower = Range.clip(movingJoystick + (rotatingJoystick * rotationSpeed), -1.0, 1.0);
        backLeftPower = Range.clip(movingJoystick - (rotatingJoystick * rotationSpeed), -1.0, 1.0);

        // This tells the motors how fast to go
        frontRightDrive.setPower(frontRightPower);
        frontLeftDrive.setPower(frontLeftPower);
        backRightDrive.setPower(backRightPower);
        backLeftDrive.setPower(backLeftPower);

    }

    public void telemetryOutput() {

        telemetry.addData("Front Right Drive", "%.2f", frontRightPower);
        telemetry.addData("Front Left Drive", "%.2f", frontLeftPower);
        telemetry.addData("Back Right Drive", "%.2f", backRightPower);
        telemetry.addData("Back Left Drive", "%.2f", backLeftPower);

    }

}
