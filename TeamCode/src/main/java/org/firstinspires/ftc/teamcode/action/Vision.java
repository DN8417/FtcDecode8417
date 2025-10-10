package org.firstinspires.ftc.teamcode.action;

import android.util.Size;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import org.firstinspires.ftc.vision.apriltag.*;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

import java.util.List;

/** This is the class for our Vision Detection
 * This allows us to detect color in HSV and RGB and detect april tags
 */
public class Vision {

    Telemetry telemetry;
    PredominantColorProcessor colorSensor;
    AprilTagProcessor aprilTag;
    VisionPortal portal;
    public void init(@NonNull OpMode opMode) {

        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        colorSensor = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.1, 0.1, 0.1, -0.1))
                .setSwatches(

                        // These adds the different colors the robot can see
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE,
                        PredominantColorProcessor.Swatch.YELLOW,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE)
                .build();

        // This method allows us to detect april tags
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11) // This sets the family of tags the robot works with
                .build();

        // This method sets which camera we will use
        portal = new VisionPortal.Builder()
                .addProcessors(colorSensor, aprilTag)
                .setCameraResolution(new Size(320, 240))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();
    }

    /** Predominant Color Processor gives us what color the robot sees
     *
     * @return PredominantColorProcessor with a closestSwatch and Integer
     */
    public PredominantColorProcessor.Result result() {
        return colorSensor.getAnalysis();
    }

    /** Closest Swatch gives us the closestSwatch as a String
     * The method returns the closest swatch and makes sure it is not null
     * @return closestSwatch as a String
     */
    public String closestSwatch() {
        return (result().closestSwatch == null) ? "null" : result().closestSwatch.toString();
    }

    /** getColor passes in a String of Red Green or Blue and then converts that to the rgb value
     * if the color is not Red Green or Blue then it returns -1
     * @param color passes in the color we want as a String and must be Red Green or Blue
     * @return getColor as the int value of the color we passed in
     */
    public int getColor(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return Color.red(result().rgb);
            case "green":
                return Color.green(result().rgb);
            case "blue":
                return Color.blue(result().rgb);
        }
        return -1;
    }

    /** This converts the rgb value to HSV
     * This method runs the calculations to convert a rgb value to a HSV value
     * @return A Double array of Hue Saturation and Value
     */
    public double[] getHSVValue() {
        double red = Math.min(Math.max(getColor("red"), 0), 255);
        double green = Math.min(Math.max(getColor("green"), 0), 255);
        double blue = Math.min(Math.max(getColor("blue"), 0), 255);
        red /= 255;
        green /= 255;
        blue /= 255;
        double valueMax = Math.max(red, Math.max(blue, green));
        double valueMin = Math.min(red, Math.min(blue, green));
        double deltaValue = valueMax - valueMin;
        double hue, saturation, value;
        if (deltaValue == 0) {
            hue = 0;
        } else if (valueMax == red) {
            hue = ((green - blue) / deltaValue) % 6;
        } else if (valueMax == green) {
            hue = (blue - red) / deltaValue + 2;
        } else {
            hue = (red - green) / deltaValue + 4;
        }
        hue *= 60;
        if(hue < 0) {
            hue += 360;
        }

        value = valueMax;

        saturation = (value == 0) ? 0 : deltaValue / value;
        saturation *= 100;
        value *= 100;
        return new double[] {hue, saturation, value};
    }

    /** AprilTagSearch allows us to get the april tag id
     *
     * @param id passes in the id number for the april tag we want to detect
     * @return AprilTagDetection with the metadata
     */
    public AprilTagDetection aprilTagSearch(int id) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (id == detection.metadata.id) {
                    return detection;
                }
                /*
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                */
            }
        }   // end for() loop

        // Add "key" information to telemetry
        /*
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
        */
        return null;
    }

    /** Outputs Telemetry for this class
     * Outputs the Telemetry for the RGB and HSV values to the driver station
     */
    public void telemetryOutput() {
        telemetry.addData("closestSwatch", closestSwatch());
        telemetry.addData("RGB", String.format("R %4d, G %4d, B %4d", getColor("red"), getColor("green"), getColor("blue")));
        telemetry.addData("HSV", String.format("H %4.0f, S %4.1f, V %4.1f", getHSVValue()[0], getHSVValue()[1], getHSVValue()[2]));
        AprilTagDetection detection = aprilTagSearch(15);
        if (detection != null) {
            telemetry.addData("aprilTagName", String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
            telemetry.addData("aprilTagCoords", String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
        }
    }
}
