package org.firstinspires.ftc.teamcode.init;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.mecanumDrive;

@TeleOp(name="TeleOp")
public class Teleop extends OpMode {

    org.firstinspires.ftc.teamcode.action.mecanumDrive mecanumDrive = new mecanumDrive();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        mecanumDrive.init(this);
    }

    @Override
    public void loop() {
        telemetry.addData("Status", "Running");

        mecanumDrive.drive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
        mecanumDrive.telemetryOutput();

    }
}
