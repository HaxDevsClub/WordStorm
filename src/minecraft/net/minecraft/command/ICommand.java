package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;

public interface ICommand extends Comparable
{
    /**
     * Gets the name of the command
     */
    String getCommandName();

    /**
     * Gets the usage string for the command.
     *  
     * @param sender The {@link ICommandSender} who is requesting usage details.
     */
    String getCommandUsage(ICommandSender sender);

    /**
     * Gets a list of aliases for this command
     */
    List getCommandAliases();

    /**
     * Callback when the command is invoked
     *  
     * @param sender The {@link ICommandSender sender} who executed the command
     * @param args The arguments that were passed with the command
     */
    void processCommand(ICommandSender sender, String[] args) throws CommandException;

    /**
     * Returns true if the given command sender is allowed to use this command.
     *  
     * @param sender The CommandSender
     */
    boolean canCommandSenderUseCommand(ICommandSender sender);

    /**
     * Return a list of options when the user types TAB
     *  
     * @param sender The {@link ICommandSender sender} who pressed TAB
     * @param args The arguments that were present when TAB was pressed
     * @param pos The block that the player is targeting, <b>May be {@code null}</b>
     */
    List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos);

    /**
     * Return whether the specified command parameter index is a username parameter.
     *  
     * @param args The arguments that were given
     * @param index The argument index that we are checking
     */
    boolean isUsernameIndex(String[] args, int index);
}
