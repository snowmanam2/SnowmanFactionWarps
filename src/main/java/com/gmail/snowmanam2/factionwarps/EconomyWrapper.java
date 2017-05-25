package com.gmail.snowmanam2.factionwarps;

import java.math.BigDecimal;

import org.bukkit.entity.Player;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class EconomyWrapper {
	
	public static BigDecimal getMoney (Player player) {
		BigDecimal result = new BigDecimal(0);
		try {
			result = Economy.getMoneyExact(player.getName());
		} catch (UserDoesNotExistException e) {
			result = new BigDecimal(0);
		}
		
		return result;
	}
	
	public static void subtract(Player player, BigDecimal amount) {
		try {
			Economy.substract(player.getName(), amount);
			String amountString = Economy.format(amount);
			player.sendMessage(Messages.get("moneyWithdraw", amountString));
		} catch (UserDoesNotExistException e) {
			
		} catch (NoLoanPermittedException e) {
			
		} catch (ArithmeticException e) {
			
		}
	}
	
	public static void add(Player player, BigDecimal amount) {
		try {
			Economy.add(player.getName(), amount);
			String amountString = Economy.format(amount);
			player.sendMessage(Messages.get("moneyDeposit", amountString));
		} catch (NoLoanPermittedException e) {
			
		} catch (ArithmeticException e) {
			
		} catch (UserDoesNotExistException e) {
			
		}
	}
	
	public static String formatMoney (BigDecimal amount) {
		return Economy.format(amount);
	}
	
}
