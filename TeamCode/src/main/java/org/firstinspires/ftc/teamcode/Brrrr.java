package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

// This holds all the movement code for the robot. Like turn right and left. Just to make it easier to use throughout the code base - Tim
public class Brrrr {

    // This is all the motors.
    public DcMotor front_left_motor;
    public DcMotor front_right_motor;
    public DcMotor back_left_motor;
    public DcMotor back_right_motor;

    ///  Put in the name of the motors that are in the computer thingy. It will not work other wise. - Tim
    public Brrrr(HardwareMap map, String name_front_left_motor, String name_front_right_motor, String name_back_left_motor, String name_back_right_motor) {
        front_left_motor = map.get(DcMotor.class, name_front_left_motor);
        front_right_motor = map.get(DcMotor.class, name_front_right_motor);
        back_left_motor = map.get(DcMotor.class, name_back_left_motor);
        back_right_motor = map.get(DcMotor.class, name_back_right_motor);

        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        front_left_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        back_left_motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /// This updates every loop and takes in the gamepad. - Tim
    public void update_input(Gamepad gamepad) {
        front_left_motor.setPower(gamepad.left_stick_y + (-gamepad.left_stick_x - gamepad.right_stick_x));
        front_right_motor.setPower(gamepad.left_stick_y - (-gamepad.left_stick_x - gamepad.right_stick_x));
        back_left_motor.setPower(gamepad.left_stick_y - (-gamepad.left_stick_x + gamepad.right_stick_x));
        back_right_motor.setPower(gamepad.left_stick_y - gamepad.left_stick_x - gamepad.right_stick_x);
    }

    
}
