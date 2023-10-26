package frc.robot.commands.drive;

import frc.robot.constants.OIConstants;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.SwerveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopSwerve extends CommandBase {    
    private SwerveSubsystem s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;

    public TeleopSwerve(SwerveSubsystem s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/

        //SlewRateLimiter translationLimiter = new SlewRateLimiter(0.5);
        //SlewRateLimiter strafeLimiter = new SlewRateLimiter(0.5);
        
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), OIConstants.kDriverDeadzone);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), OIConstants.kDriverDeadzone);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), OIConstants.kTurnDeadzone);

        /* Drive */
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(SwerveConstants.maxSpeed), 
            rotationVal * SwerveConstants.maxAngularVelocity, 
            !robotCentricSup.getAsBoolean(), 
            true
        );
    }
}