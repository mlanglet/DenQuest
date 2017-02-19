package den.engine.utility;

public class Constants {
	public enum DIRECTION { NORTH, EAST, SOUTH, WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST }
	public enum SCENE { MAIN_MENU, LEVEL_1, LEVEL_2, LEVEL_3, OPTIONS }
	public enum DAMAGE_TYPE { PHYSICAL_MELEE, PHYSICAL, MAGIC, HP_REMOVAL }
	public enum ENTITY_TYPE { STRUCTURE, CREATURE }
	public enum WALL_TYPE { LONG, SHORT }
	
	public static DIRECTION getOppositeDirection(DIRECTION d){
		switch(d){
		case EAST:
			return DIRECTION.WEST;
		case NORTH:
			return DIRECTION.SOUTH;
		case NORTH_EAST:
			return DIRECTION.SOUTH_WEST;
		case NORTH_WEST:
			return DIRECTION.SOUTH_EAST;
		case SOUTH:
			return DIRECTION.NORTH;
		case SOUTH_EAST:
			return DIRECTION.NORTH_WEST;
		case SOUTH_WEST:
			return DIRECTION.NORTH_EAST;
		case WEST:
			return DIRECTION.EAST;
		default:
			break;
		}
		return d;
	}
}
