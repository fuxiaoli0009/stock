package com.common.command;

public class Command {

	@FunctionalInterface
	public interface Executor {
		Object exec(Command command);
	}

	public final String name;
	public final String json;

	public Command(String name, String json) {
		this.name = name;
		this.json = json;
	}
}
