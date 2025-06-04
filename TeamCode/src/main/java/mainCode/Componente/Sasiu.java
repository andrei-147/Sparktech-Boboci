package mainCode.Componente;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
public class Sasiu {
    private static Gamepad gamepad1 = null;
    private static DcMotor rightFrontDrive = null;
    private static DcMotor leftFrontDrive = null;
    private static DcMotor rightBackDrive = null;
    private static DcMotor leftBackDrive = null;
    private static double speed = 1;
    public void InitializeComponents(Gamepad g1, HardwareMap hw_map) {
        gamepad1 = g1;
        rightFrontDrive = hw_map.get(DcMotor.class, "rightFrontDrive");
        leftFrontDrive = hw_map.get(DcMotor.class, "leftFrontDrive");
        rightBackDrive = hw_map.get(DcMotor.class, "rightBackDrive");
        leftBackDrive = hw_map.get(DcMotor.class, "leftBackDrive");

        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);

        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void RunInsideLoop() {
        double max;

        double straight = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double orientation = gamepad1.right_stick_x;

        double leftFrontPower = speed * (straight + lateral + orientation);
        double rightFrontPower = speed * (straight - lateral - orientation);
        double leftBackPower = speed * (straight - lateral + orientation);
        double rightBackPower = speed * (straight + lateral - orientation);

        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }
}
