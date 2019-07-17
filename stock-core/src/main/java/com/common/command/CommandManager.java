package com.common.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CommandManager {
	
	private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);

	private static HashMap<String, Command.Executor> executors = new HashMap<>();

	public static synchronized void register(String command, Command.Executor executor) {
		if (executors.containsKey(command)) {
			throw new RuntimeException(command + ":命令重复注册");
		}
		executors.put(command, executor);
		logger.info("CommandManager注册command:{},executor:{}", command, executor);
	}

	public static synchronized void remove(String command) {
		logger.info("删除command:{}", command);
		executors.remove(command);
	}

	public static synchronized void clear() {
		logger.info("清空executors");
		executors.clear();
	}

	public static Object command(Command command) {
		logger.info("执行command: " + command.name);
		Command.Executor executor = executors.get(command.name);
		if (executor != null) {
			return executor.exec(command);
		} else {
			throw new RuntimeException("Not command name!");
		}
	}
}
