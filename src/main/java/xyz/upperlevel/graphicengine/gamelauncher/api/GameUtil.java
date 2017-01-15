package xyz.upperlevel.graphicengine.gamelauncher.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameUtil {

	public static boolean testGroupId(String groupId) {
		return groupId.matches("^[a-zA-Z.]+$");
	}

	public static boolean testId(String id) {
		return id.matches("^[a-zA-Z.-]+$");
	}

	public static boolean testDisplayName(String displayName) {
		return true;
	}

	public static boolean testVersion(String version) {
		return version.matches("^[0-9a-zA-Z.-]+$");
	}
}