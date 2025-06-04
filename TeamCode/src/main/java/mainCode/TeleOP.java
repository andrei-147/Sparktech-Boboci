//package mainCode;
//
//import java.lang.Math;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//@TeleOp(name="TeleOP Sasiu", group="Linear OpMode")
//
//public class TeleOP extends LinearOpMode {
//
//    private boolean wasYpressed = false;
//    private boolean wasApressed = false;
//
//    @Override
//    public void runOpMode()
//    {
//        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
//        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
//        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
//        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
//
//        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
//        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
//        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
//        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
//
//        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        waitForStart();
//        telemetry.addData("Status", "Running");
//        telemetry.update();
//
//        while(opModeIsActive())
//        {
//
//        }
//    }
//}
