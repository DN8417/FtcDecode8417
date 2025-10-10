package org.firstinspires.ftc.teamcode.init;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Backup Auto", group ="Mini Autos")
public class BackupAuto extends LinearOpMode {

    public DcMotor frontRightDrive;
    public DcMotor frontLeftDrive;
    public DcMotor backRightDrive;
    public DcMotor backLeftDrive;


    public ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws  InterruptedException {

        frontRightDrive = hardwareMap.get(DcMotor.class, "Front Right");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "Front Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "Back Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "Back Left");

        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        mecanumDrive(-0.75, 0.75, 0.7, -0.75, 0.35);
        mecanumDrive(0.75, 0.75, 0.75, 0.75, 0.7);

    }

    public void mecanumDrive(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower, double seconds) {

        timer.reset();

        while(opModeIsActive() && (timer.seconds() <= seconds )) {

            frontRightDrive.setPower(frontRightPower);
            frontLeftDrive.setPower(frontLeftPower);
            backRightDrive.setPower(backRightPower);
            backLeftDrive.setPower(backLeftPower);

        }

    }
}
