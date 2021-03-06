package com.github.vertical_blank.sqlformatter.core.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

	private static final String ESCAPE_REGEX = Stream.of("^", "$", "\\", ".", "*", "+", "*", "?", "(", ")", "[", "]", "{", "}", "|")
			.map(spChr -> "(\\" + spChr + ")").collect(Collectors.joining("|"));
	public static final Pattern ESCAPE_REGEX_PATTERN = Pattern.compile(ESCAPE_REGEX);


	public static <T> List<T> nullToEmpty(List<T> ts) {
		if (ts == null) {
			return Collections.emptyList();
		} else {
			return ts;
		}
	}

	public static String trimEnd(String s) {
		int endIndex = s.length();
		char[] chars = s.toCharArray();
		while(endIndex > 0 && (chars[endIndex  - 1] == ' ' || chars[endIndex  - 1] == '\n' || chars[endIndex  - 1] == '\r')) {
			endIndex--;
		}
		return new String(chars, 0, endIndex);
	}

	public static String escapeRegExp(String s) {
		return ESCAPE_REGEX_PATTERN.matcher(s).replaceAll("\\\\$0");
	}

	@SafeVarargs
	public static <R> R firstNotnull(Supplier<R>... sups) {
		for (Supplier<R> sup : sups) {
			R ret = sup.get();
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	@SafeVarargs
	public static <R> Optional<R> firstPresent(Supplier<Optional<R>>... sups) {
		for (Supplier<Optional<R>> sup : sups) {
			Optional<R> ret = sup.get();
			if (ret.isPresent()) {
				return ret;
			}
		}
		return Optional.empty();
	}

}

