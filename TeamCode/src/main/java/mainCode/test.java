package mainCode;

import java.lang.Math;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOP brat", group="Linear OpMode")

/*
* TO-DO LIST:
*
* - sa modificam codul astfel incat sa respecte toate conventiile
* */


public class test extends LinearOpMode {

    private DcMotor MotorIntakeOuttakeStanga = null;
    private DcMotor MotorIntakeOuttakeDreapta = null;

    private int DirectieMiscareBrat = 0;
    private double moveSpeed = 1;
    private boolean wasGamepad1upPressed = false;
    private boolean wasGamepad1downPressed = false;
    private boolean wasGamepad1leftPressed = false;
    private boolean wasGamepad1rightPressed = false;

    @Override
    public void runOpMode()
    {
        MotorIntakeOuttakeDreapta = hardwareMap.get(DcMotor.class, "MotorBratDreapta");
        MotorIntakeOuttakeStanga = hardwareMap.get(DcMotor.class, "MotorBratStanga");

        MotorIntakeOuttakeStanga.setDirection(DcMotor.Direction.FORWARD);
        MotorIntakeOuttakeDreapta.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();

        while(opModeIsActive())
        {
            DirectieMiscareBrat = 0;
            if (gamepad1.y) DirectieMiscareBrat += 1;
            if (gamepad1.a) DirectieMiscareBrat -= 1;
            MotorIntakeOuttakeStanga.setPower(moveSpeed * DirectieMiscareBrat);
            MotorIntakeOuttakeDreapta.setPower(moveSpeed * DirectieMiscareBrat);

            if (gamepad1.dpad_up && !wasGamepad1upPressed) wasGamepad1upPressed = true;
            if (!gamepad1.dpad_up && wasGamepad1upPressed) moveSpeed = Math.min(moveSpeed + 0.1, 1);
            if (!gamepad1.dpad_up) wasGamepad1upPressed = false;

            if (gamepad1.dpad_down && !wasGamepad1downPressed) wasGamepad1downPressed = true;
            if (!gamepad1.dpad_down && wasGamepad1downPressed) moveSpeed = Math.max(moveSpeed - 0.1, 0.1);
            if (!gamepad1.dpad_down) wasGamepad1downPressed = false;

            telemetry.addData("Viteza Miscare Brat", moveSpeed);

            telemetry.update();

        }


    }


}
