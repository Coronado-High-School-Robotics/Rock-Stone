package org.firstinspires.ftc.teamcode.opModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.pow

@TeleOp(name = "¯\\_(ツ)_/¯")
class dumby : LinearOpMode() {
    private lateinit var leftFly: DcMotor
    private lateinit var motor1FR: DcMotor
    private lateinit var motor3BR: DcMotor
    private lateinit var rFly: DcMotor
    private lateinit var motor0FL: DcMotor
    private lateinit var motor2BL: DcMotor
    private lateinit var intake0: CRServo
    private lateinit var intake1: CRServo
    private lateinit var intake2: CRServo
    private lateinit var intake3: CRServo
    private lateinit var cylinder: DcMotor
    private lateinit var push: Servo

    var fireCheck: Int = 0
    var maxDrivePower: Int = 0
    var cylinderHome: Double = 0.0

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    override fun runOpMode() {

        leftFly = hardwareMap.get(DcMotor::class.java, "LFly")


        motor1FR = hardwareMap.get(DcMotor::class.java, "Motor3FR")
        motor3BR = hardwareMap.get(DcMotor::class.java, "Motor1BR")
        rFly = hardwareMap.get(DcMotor::class.java, "RFly")
        motor0FL = hardwareMap.get(DcMotor::class.java, "Motor0FL")
        motor2BL = hardwareMap.get(DcMotor::class.java, "Motor2BL")
        intake0 = hardwareMap.get(CRServo::class.java, "intake0")
        intake1 = hardwareMap.get(CRServo::class.java, "intake1")
        intake2 = hardwareMap.get(CRServo::class.java, "intake2")
        intake3 = hardwareMap.get(CRServo::class.java, "intake3")
        cylinder = hardwareMap.get(DcMotor::class.java, "cylinder")
        push = hardwareMap.get(Servo::class.java, "push")

        //cylinder.mode = DcMotor.RunMode.

        // Put initialization blocks here.
        motor1FR.direction = DcMotorSimple.Direction.REVERSE
        motor3BR.direction = DcMotorSimple.Direction.REVERSE
        leftFly.direction = DcMotorSimple.Direction.FORWARD
        rFly.direction = DcMotorSimple.Direction.REVERSE
        motor0FL.direction = DcMotorSimple.Direction.FORWARD
        motor2BL.direction = DcMotorSimple.Direction.FORWARD
        intake0.direction = DcMotorSimple.Direction.FORWARD
        intake1.direction = DcMotorSimple.Direction.REVERSE
        intake2.direction = DcMotorSimple.Direction.REVERSE
        intake3.direction = DcMotorSimple.Direction.FORWARD
        maxDrivePower = 1
        cylinderHome = -0.005

        cylinder.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        cylinder.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        cylinder.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        cylinder.targetPosition = 0

        if (cylinder is DcMotorEx) {
            (cylinder as DcMotorEx).setVelocityPIDFCoefficients(10.0, 3.0, 3.00, 7.0)
        }

        var LongRange = false
        var LauncherOn = false

        var right_bumper = false
        var left_bumper = false

        waitForStart()
        if (opModeIsActive()) {
            //cylinder.position = cylinderHome
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                Mecanum_Drive()
                if (gamepad1.right_trigger != 0f) {
                    intake()
                } else {
                    intakeoff()
                }

                // Checks if right_bumper is pressed or held
                if (!gamepad2.right_bumper) {
                    right_bumper = false
                }


                // This only happens when the right_bumper gets pressed, not held
                if (gamepad2.right_bumper && !right_bumper) {
                    if (!LauncherOn) {
                        LongRange = true
                        LauncherOn = true
                    } else {
                        if (LongRange) {
                            LauncherOn = false
                        } else {
                            LongRange = true
                        }
                    }
                    right_bumper = true
                }


                // Checks if right_bumper is pressed or held
                if (!gamepad2.left_bumper) {
                    left_bumper = false
                }


                // This only happens when the right_bumper gets pressed, not held
                if (gamepad2.left_bumper && !left_bumper) {
                    if (!LauncherOn) {
                        LongRange = false
                        LauncherOn = true
                    } else {
                        if (!LongRange) {
                            LauncherOn = false
                        } else {
                            LongRange = false
                        }
                    }
                    left_bumper = true
                }

                if (gamepad2.x) {
                    setToOutake(1)
                }
                if (gamepad2.a) {
                    setToOutake(2)
                }
                if (gamepad2.b) {
                    setToOutake(3)
                }
                cylinder.mode = DcMotor.RunMode.RUN_TO_POSITION
                while (opModeIsActive() && cylinder.isBusy) {
                    cylinder.power = 0.4
                }

                if (LauncherOn) {
                    if (LongRange) {
                        Launch_Far()
                    } else {
                        Launch_Near()
                    }
                } else {
                    Lauch_Off()
                }
                while (gamepad1.left_trigger == 1f) {
                    intakeOut()
                }

                //cylinder2()
                if (fireCheck == 1 && gamepad2.right_trigger > 0.6) {
                    push.position = 0.0
                    sleep(250)
                } else {
                    push.position = 0.37
                }
            }
        }
    }

    /**
     * Describe this function...
     */
    private fun Mecanum_Drive() {
        // Mechanum Omnidirectional Drive
        motor0FL.power = ((gamepad2.left_stick_y + (-gamepad2.left_stick_x - gamepad2.right_stick_x)) * 0.55)
        motor1FR.power = ((gamepad2.left_stick_y - (-gamepad2.left_stick_x - gamepad2.right_stick_x)) * 0.55)
        motor2BL.power = ((gamepad2.left_stick_y - (-gamepad2.left_stick_x + gamepad2.right_stick_x)) * 0.55)
        motor3BR.power = ((gamepad2.left_stick_y + -gamepad2.left_stick_x + gamepad2.right_stick_x) * 0.55)
        motor0FL.power = (
            (gamepad1.left_stick_y + (-gamepad1.left_stick_x - gamepad1.right_stick_x)).toDouble()
                .pow(5.0) * maxDrivePower
        )
        motor1FR.power = (
            (gamepad1.left_stick_y - (-gamepad1.left_stick_x - gamepad1.right_stick_x)).toDouble()
                .pow(5.0) * maxDrivePower
        )
        motor2BL.power = (
            (gamepad1.left_stick_y - (-gamepad1.left_stick_x + gamepad1.right_stick_x)).toDouble()
                .pow(5.0) * maxDrivePower
        )
        motor3BR.power = (
            (gamepad1.left_stick_y + -gamepad1.left_stick_x + gamepad1.right_stick_x).toDouble()
                .pow(5.0) * maxDrivePower
        )
    }

    /**
     * BOOOO! Happy Halloween.................................................Go to hell
     */
    val ticksPerDegrees = 28*19.2 / 360
    val intakePosition1 = 180.0
    val intakePosition2 = 60.0
    val intakePosition3 = -60.0
    private fun setToIntakePosition(position: Int) {
        //cylinder.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        when (position) {
            1 -> cylinder.targetPosition = ((intakePosition1 * ticksPerDegrees).toInt())
            2 -> cylinder.targetPosition = (intakePosition2 * ticksPerDegrees).toInt()
            3 -> cylinder.targetPosition = (intakePosition3 * ticksPerDegrees).toInt()
            else -> telemetry.addData("You Stupid Moron", " There are only 3!! POSITIONS")
        }
        cylinder.mode = DcMotor.RunMode.RUN_TO_POSITION
        while (opModeIsActive() && cylinder.isBusy) {
            cylinder.setPower(0.4)
        }
    }

    val outakePosition1 = 0
    val outakePosition2 = -120
    val outakePosition3 = 120
    private fun setToOutake(position: Int) {
        when (position) {
            1 -> cylinder.setTargetPosition((outakePosition1 * ticksPerDegrees).toInt())
            2 -> cylinder.targetPosition = (outakePosition2 * ticksPerDegrees).toInt()
            3 -> cylinder.targetPosition = (outakePosition3 * ticksPerDegrees).toInt()
            else -> telemetry.addData("You Stupid Moron", " There are only 3!! POSITIONS")
        }
        cylinder.mode = DcMotor.RunMode.RUN_TO_POSITION
//        while (opModeIsActive() && cylinder.isBusy) {
//            cylinder.power = 0.4
//        }
    }


    /**
     * Describe this function...
     */
    private fun Launch_Near() {
        leftFly.power = (0.37)
        rFly.power = (0.37)
        fireCheck = 1
    }

    /**
     * Describe this function...
     */
    private fun Launch_Far() {
        leftFly.power = (0.41)
        rFly.power = (0.41)
        fireCheck = 1
    }

    /**
     * Describe this function...Nah
     */
    private fun intake() {
        intake0.power = (1.0)
        intake1.power = (1.0)
        intake2.power = (1.0)
        intake3.power = (1.0)
    }

    private fun intakeOut() {
        intake0.power = (1.0)
        intake1.power = (1.0)
        intake2.power = (1.0)
        intake3.power = (1.0)
    }

    /**
     * Describe this function...
     */
    private fun Lauch_Off() {
        leftFly.power = (0.0)
        rFly.power = (0.0)
        fireCheck = 0
    }

    /**
     * Describe this function...
     */
    private fun intakeoff() {
        intake0.power = (0.0)
        intake1.power = (0.0)
        intake2.power = (0.0)
        intake3.power = (0.0)
    }
}

