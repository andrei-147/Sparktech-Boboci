package mainCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TEST BRAT", group = "Linear OpMode")
public class testBratMotoare extends LinearOpMode{

    private double speed = 0.3;
    private DcMotor MotorDreapta = null;
    private DcMotor MotorStanga = null;
    private boolean wasGamepad1XPressed = false;
    private boolean wasGamepad1BPressed = false;
    private boolean wasGamepad1upPressed = false;
    private boolean wasGamepad1downPressed = false;
    @Override
    public void runOpMode() {
        MotorDreapta = hardwareMap.get(DcMotor.class, "MotorBratDreapta");
        MotorStanga = hardwareMap.get(DcMotor.class, "MotorBratStanga");

        MotorDreapta.setDirection(DcMotor.Direction.FORWARD);
        MotorStanga.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x && !wasGamepad1XPressed) wasGamepad1XPressed = true;
            if (!gamepad1.x && wasGamepad1XPressed) {
                ElapsedTime timer = new ElapsedTime();
                while (timer.seconds() < 0.5 && opModeIsActive()) {
                    MotorDreapta.setPower(speed);
                }
            }
            if (!gamepad1.x) wasGamepad1XPressed = false;

            if (gamepad1.b && !wasGamepad1BPressed) wasGamepad1BPressed = true;
            if (!gamepad1.b && wasGamepad1BPressed) {
                ElapsedTime timer = new ElapsedTime();
                while (timer.seconds() < 0.5 && opModeIsActive()) {
                    MotorStanga.setPower(speed);
                }
            }
            if (!gamepad1.b) wasGamepad1BPressed = false;

            if (gamepad1.dpad_up && !wasGamepad1upPressed) wasGamepad1upPressed = true;
            if (!gamepad1.dpad_up && wasGamepad1upPressed) speed += 0.02;
            if (!gamepad1.dpad_up) wasGamepad1upPressed = false;

            if (gamepad1.dpad_down && !wasGamepad1downPressed) wasGamepad1downPressed = true;
            if (!gamepad1.dpad_down && wasGamepad1downPressed) speed -= 0.02;
            if (!gamepad1.dpad_down) wasGamepad1downPressed = false;

            telemetry.addData("VITEZA", speed);
            telemetry.update();
        }
    }
}
