package xyz.upperlevel.graphicengine.gamelauncher.api;

import java.util.Map;

public class GameIdentity {

    public final String
            groupId,
            id,
            version,
            displayName;

    public GameIdentity(String groupId, String id, String version) {
        this(groupId, id, version, id);
    }

    public GameIdentity(String groupId, String id, String version, String displayName) {
        if (!GameUtil.testGroupId(groupId))
            throw new IllegalArgumentException("GroupId syntax error");
        if (!GameUtil.testId(id))
            throw new IllegalArgumentException("Id syntax error");
        if (!GameUtil.testVersion(version))
            throw new IllegalArgumentException("Version syntax error");
        if (!GameUtil.testDisplayName(displayName))
            throw new IllegalArgumentException("DisplayName syntax error");
        this.groupId = groupId;
        this.id = id;
        this.version = version;
        this.displayName = displayName;
    }

    /**
     * Gets game group id. Usually the inverted domain of the developers who made it.
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Gets game id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets game version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets game display name. This will be shown in graphic engine launcher.
     */
    public String getDisplayName() {
        return displayName;
    }

    public static GameIdentity load(Map<String, Object> data) {
        if (!data.containsKey(GameNeed.GAME_GROUPID.getOnly()))
            throw new IllegalArgumentException("GroupId doesn't found");
        String id = (String) data.get(GameNeed.GAME_ID.getOnly());
        if (!data.containsKey(GameNeed.GAME_ID.getOnly()))
            throw new IllegalArgumentException("Id doesn't  found");
        if (!data.containsKey(GameNeed.GAME_VERSION.getOnly()))
            throw new IllegalArgumentException("Version doesn't found");
        String displayName = data.containsKey(GameNeed.GAME_DISPLAYNAME.getOnly()) ? (String) data.get(GameNeed.GAME_DISPLAYNAME.getOnly()) : id;
        return new GameIdentity(
                (String) data.get(GameNeed.GAME_GROUPID.getOnly()),
                (String) data.get(GameNeed.GAME_ID.getOnly()),
                (String) data.get(GameNeed.GAME_VERSION.getOnly()),
                displayName
        );
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id + "-" + version;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GameIdentity &&
                groupId.equals(((GameIdentity) obj).getGroupId()) &&
                id.equals(((GameIdentity) obj).getId()) &&
                version.equals(((GameIdentity) obj).getVersion());
    }
}