package mainCode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Test Roti", group="Linear OpMode")

/*
* TO-DO LIST:
*
* - sa modificam codul astfel incat sa respecte toate conventiile
* */


public class test extends LinearOpMode {


    private DcMotor RightFrontDrive = null;

    private DcMotor LeftFrontDrive = null;

    private DcMotor RightBackDrive = null;

    private DcMotor LeftBackDrive = null;

    private final double speed = 0.6;

    @Override

    public void runOpMode()
    {
        RightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");

        LeftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");

        RightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

        LeftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");


        RightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        RightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        LeftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        LeftBackDrive.setDirection(DcMotor.Direction.REVERSE);

        if (RightFrontDrive == null) telemetry.addData("Motors", "RightFront is null");
        if (RightBackDrive == null) telemetry.addData("Motors", "RightBack is null");
        if (LeftFrontDrive == null) telemetry.addData("Motors", "LeftFront is null");
        if (LeftBackDrive == null) telemetry.addData("Motors", "LeftBack is null");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status", "Running");

        while(opModeIsActive())
        {
            if (gamepad1.left_stick_x * gamepad1.left_stick_y != 0)
            {
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

                LeftFrontDrive.setPower(leftFrontPower);
                RightFrontDrive.setPower(rightFrontPower);
                LeftBackDrive.setPower(leftBackPower);
                RightBackDrive.setPower(rightBackPower);
            }
            else
            {
                if (gamepad1.y && gamepad1.x) LeftFrontDrive.setPower(speed);
                else LeftFrontDrive.setPower(0);
                if (gamepad1.y && gamepad1.b) RightFrontDrive.setPower(speed);
                else RightFrontDrive.setPower(0);
                if (gamepad1.a && gamepad1.x) LeftBackDrive.setPower(speed);
                else LeftBackDrive.setPower(0);
                if (gamepad1.a && gamepad1.b) RightBackDrive.setPower(speed);
                else RightBackDrive.setPower(0);

            }
        }


    }


}
