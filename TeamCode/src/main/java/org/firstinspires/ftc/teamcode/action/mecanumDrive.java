package org.firstinspires.ftc.teamcode.action;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class mecanumDrive {

    double frontRightPower;
    double frontLeftPower;
    double backRightPower;
    double backLeftPower;
    DcMotor frontRightDrive;
    DcMotor frontLeftDrive;
    DcMotor backRightDrive;
    DcMotor backLeftDrive;

    public static double rotationSpeed = 0.75;

    public void init(@NonNull OpMode opMode) {

        HardwareMap hardwareMap = opMode.hardwareMap;

        frontRightDrive = hardwareMap.get(DcMotor.class, "Front Right");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "Front Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "Back Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "Back Left");

    }

    public void drive(double movingJoystick, double rotatingJoystick) {

        frontRightPower = Range.clip(movingJoystick + (rotatingJoystick * rotationSpeed), -1.0, 1.0);
        frontLeftPower = Range.clip(movingJoystick - (rotatingJoystick * rotationSpeed), -1.0, 1.0);
        backRightPower = Range.clip(movingJoystick + (rotatingJoystick * rotationSpeed), -1.0, 1.0);
        backLeftPower = Range.clip(movingJoystick - (rotatingJoystick * rotationSpeed), -1.0, 1.0);

        frontRightDrive.setPower(frontRightPower);
        frontLeftDrive.setPower(frontLeftPower);
        backRightDrive.setPower(backRightPower);
        backLeftDrive.setPower(backLeftPower);

    }

}
