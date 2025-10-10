package org.firstinspires.ftc.teamcode.init;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.action.mecanumDrive;
import org.firstinspires.ftc.teamcode.action.Intake;

@TeleOp (name = "TeleOp", group = "Main")
public class Teleop extends OpMode {
    mecanumDrive mecanumDrive = new mecanumDrive();
    Intake intake = new Intake();
    boolean mode = true;
    ElapsedTime swapDelay = new ElapsedTime();
    @Override
    public void init() {
        //Initialize our motors
        mecanumDrive.init(this);
        intake.init(this);

    }

    public void start() {
        mecanumDrive.runWithoutEncoder();
        intake.init(this);

    }

    @Override
    public void loop() {

        if(gamepad1.back || gamepad2.back) {
            if(swapDelay.time() > .75) {
                mode = !mode;
                swapDelay.reset();
            }
        }

        if(mode) {
            //Controls for mecanumDrive()
            mecanumDrive.slowMode(gamepad1.left_bumper);
            mecanumDrive.driversideDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_bumper);
            mecanumDrive.telemetryOutput();

            intake.take(gamepad2.right_bumper);
            intake.give(gamepad2.left_bumper);

        } else if (!mode) {
            //Controls for mecanumDrive()
            mecanumDrive.slowMode(gamepad2.left_bumper);
            mecanumDrive.driversideDrive(gamepad2.left_stick_x, gamepad2.left_stick_y, gamepad2.right_stick_x, gamepad2.right_bumper);
            mecanumDrive.telemetryOutput();

        }

        telemetry.addData("CurrentMode: ", mode ? 0 : 1);

    }
}