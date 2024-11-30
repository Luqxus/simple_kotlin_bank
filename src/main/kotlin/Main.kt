package org.example

import java.io.IO.println
import java.io.IO.readln
import kotlin.random.Random


fun main() {

    println("Welcome to your banking system.")

    var account: Account? = null;

    while (true) {

        // if no bank account is created. Create bank account
        if (account == null) {

            println("What type of account would you like to create")


            val input: String = readInput(hint = "Choose an option (1,2 or 3)\n1. Credit account\n2. Debit account\n3. Checking account\n");

            account = Account.createAccount(input)
            if (account == null) {
                continue
            }
            println("You have create a ${account.getAccountType().type} account.")
            continue
        }

        println("What do you want to do?")
        val input: String = readInput(hint = "Choose an option (1,2)\n1. Deposit\n2. Withdraw\n3. Check balance\n");

        when {
            input  == "1" -> deposit(account)
            input == "2" -> withdraw(account)
            input == "3" -> {
                println("The ${account.getAccountType().type} account balance is R ${account.getBalance()}")
            }
            else -> {
                println("Invalid input.")
                continue;
            };
        }

    }

}

fun deposit(account: Account) {
    try {
        val amount = readInput(hint = "Enter your amount:")

        account.deposit(amount = amount.toDouble())

        println("R $amount deposited into your account. Available balance: ${account.getBalance()}")

    } catch (e: NumberFormatException) {
        println("Please enter a valid amount.")
    }
    catch (e: Throwable) {
        println("Unexpected error: ${e.message}")
    }
}

fun withdraw(account: Account) {
    try {
        val amount = readInput(hint = "Enter your amount:")

        account.withdraw(amount = amount.toDouble())

        println("R $amount withdrawn from your account. Available balance: ${account.getBalance()}")

    } catch (e: NumberFormatException) {
        println("Please enter a valid amount.")
    } catch (e: InsufficientBalanceException) {
        println(e.message)
    }
    catch (e: Throwable) {
        println("Unexpected error: ${e.message}")
    }
}


fun readInput(hint: String = ""): String {
    val input: String = readln(hint)

    if (input == "q") {
        System.exit(1)
    }

    return input;
}



class Account(
    private val accountType: AccountType,
) {
    private var balance: Long = 0
    private val account: Long = Random.nextLong( 101245713420, 101999999999)

    fun getBalance(): Double {
        return balance.div(100.0);
    }

    fun getAccountType(): AccountType {
        return accountType;
    }

    fun deposit(amount : Double) {
        if (balance >= amount -300) {
            balance = balance.plus((amount * 100).toLong())

        }
        if (balance >= amount) {
            balance = balance.plus((amount * 100).toLong())
        }
    }

    fun withdraw(amount : Double) {
        if (accountType == AccountType.Credit && balance >= amount -300) {
            balance = balance.minus((amount * 100).toLong())
            return;
        }
        if (balance >= amount) {
            balance = balance.minus((amount * 100).toLong())
            return
        }

        throw InsufficientBalanceException()
    }

    companion object {
        fun  createAccount(accountOption: String) : Account? {
            return when {
                accountOption == "1" ->  Account(AccountType.Credit);
                accountOption == "2" ->  Account(AccountType.Debit);
                accountOption == "3" ->  Account(AccountType.Check);
                else -> null;
            }
        }
    }
}


class InsufficientBalanceException : Throwable(message = "Insufficient balance") {}

enum class AccountType(val type: String) {
    Credit("credit"), Debit("debit"), Check("checking")
}
