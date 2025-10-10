package org.firstinspires.ftc.teamcode.action;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

public class Intake {
    static final DecimalFormat df = new DecimalFormat("0.00");
    Telemetry telemetry;
    private DcMotor intakeMotor;
    private double intakePower;

    public void init (@NonNull OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        intakeMotor = hardwareMap.get(DcMotor.class, "Intake");

        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void take (boolean isTaking) {

        if (isTaking) {
            intakeMotor.setPower(0.5);

        }
        else if (!isTaking) {
            intakeMotor.setPower(0.0);

        }
    }

    public void give (boolean isGiving) {

        if (isGiving) {
            intakeMotor.setPower(-0.5);

        }
        else if (!isGiving) {
            intakeMotor.setPower(0.0);

        }
    }


}