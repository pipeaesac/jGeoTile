import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TileTest {
	
	private Tile testTile = Tile.forPixels(34430575, 49899071, 19);
	

	@Test
	void fromGoogleTest() {
		Tile googleTile = Tile.fromGoogle(67,44,19);
		assert googleTile == testTile;
	}
	
	@Test
	void fromTmsTest() {
		Tile tmsTile = Tile.fromTms(67, 83, 19);
		assert tmsTile == testTile;
	}
	
	@Test
	void fromTasmaniaTest() {
		int[] tmsTasmania = {1853,758};
		Tile tasmania = Tile.fromGoogle(1853, 1289, 19);
		assert tasmania.getTms()== tmsTasmania;
	}
	
	@Test
	void fromQuadTreeTest() throws Exception {
		Tile quadTreeTile = Tile.fromQuadTree("1202211");
		assert quadTreeTile.getTms() == testTile.getTms();
		assert quadTreeTile.zoom == testTile.zoom;
	}
	
	@Test
	void crossCheckTest() throws Exception {
		Tile crossCheckTile = Tile.fromQuadTree("1202211");
		assert crossCheckTile.getTms() == testTile.getTms();
		assert crossCheckTile.zoom == testTile.zoom;
		assert crossCheckTile.getGoogle() == testTile.getGoogle();
		assert crossCheckTile.quadTree().equals(testTile.quadTree());
	}
	
	@Test
	void forPixelsTest() {
		Tile pixelsTile = Tile.forPixels(34430575, 49899071, 19);
		assert pixelsTile.getTms() == testTile.getTms();
	}

	@Test
	void forMetersTest() {
		Tile metersTile = Tile.forMeters(-9757148.442088600, 5138517.444985110, 19);
		assert metersTile.getTms() == testTile.getTms();
	}

	@Test
	void pixelBoundsTest() throws Exception {
		Tile boundsTile = Tile.fromQuadTree("1202211");
		Point pointMin = Point.fromPixel(34430464, 49899264,19);
		Point pointMax = Point.fromPixel(34430720, 49899008,19);

		Point[] bounds = { pointMin, pointMax };

		assert boundsTile.bounds() == bounds;
	}
	
	@Test
	void testTileBounds1() {
		Tile tile =  Tile.fromTms(0, 1, 1);
		Point pointMin = tile.bounds()[0];
		Point pointMax = tile.bounds()[1];
		Point expectedMin= new Point(0.0, -180.0);
		Point expectedMax = new Point(85.05, 0.0);
		assert pointMin == expectedMin;
		assert pointMax == expectedMax;
	}
	
	@Test
	void testTileBounds2() {
		Tile tile =  Tile.fromTms(1, 1, 1);
		Point pointMin = tile.bounds()[0];
		Point pointMax = tile.bounds()[1];
		Point expectedMin= new Point(0.0, 0.0);
		Point expectedMax = new Point(85.05, 180.0);
		assert pointMin == expectedMin;
		assert pointMax == expectedMax;
	}
	
	@Test
	void testTileBounds3() {
		Tile tile =  Tile.fromTms(0, 0, 1);
		Point pointMin = tile.bounds()[0];
		Point pointMax = tile.bounds()[1];
		Point expectedMin= new Point(-85.05, -180.0);
		Point expectedMax = new Point(0.0, 0.0);
		assert pointMin == expectedMin;
		assert pointMax == expectedMax;
	}
	
	@Test
	void testTileBounds4() {
		Tile tile =  Tile.fromTms(1, 0, 1);
		Point pointMin = tile.bounds()[0];
		Point pointMax = tile.bounds()[1];
		Point expectedMin= new Point(-85.05, 0.0);
		Point expectedMax = new Point(0.0, 180.0);
		assert pointMin == expectedMin;
		assert pointMax == expectedMax;
	}

	@Test
	void latitudeLongitudeTest() {
		Tile llTile = Tile.forLatitudeLongitude(41.85, -87.65, 19);
		assert llTile.getTms() == testTile.getTms();
	}

	@Test
	void pointTest() {
		Point point = Point.fromLatitudeLongitude(41.85, -87.65);
		Tile pointTile = Tile.forPoint(point, 19);
		assert pointTile.getTms() == testTile.getTms();
		assert pointTile.zoom == testTile.zoom;
	}

	@Test
	void assertTmsX() {
		assertThrows(AssertionError.class,() -> Tile.fromTms(-1, 0, 1));
		assertThrows(AssertionError.class,() -> Tile.fromTms(4, 0, 1));
	}
	
	@Test
	void notAssertTmsX() {
		assertAll(()-> Tile.fromTms(0, 0, 1));
		assertAll(()-> Tile.fromTms(3, 0, 1));
	}
	
	@Test
	void assertTmsY() {
		assertThrows(AssertionError.class,() -> Tile.fromTms(0, -1, 1));
		assertThrows(AssertionError.class,() -> Tile.fromTms(0, 4, 1));
	}
	
	@Test
	void notAssertTmsY() {
		assertAll(()-> Tile.fromTms(0, 0, 1));
		assertAll(()-> Tile.fromTms(0, 3, 1));
	}
	
	@Test
	void asserGoogleX() {
		assertThrows(AssertionError.class,() -> Tile.fromGoogle(-1, 44, 1));
		assertThrows(AssertionError.class,() -> Tile.fromGoogle(3, 44, 1));
	}
	
	@Test
	void notAssertGoogleX() {
		assertAll(()-> Tile.fromGoogle(0, 44, 1));
		assertAll(()-> Tile.fromGoogle(2, 44, 1));
	}
	
	void asserGoogleY() {
		assertThrows(AssertionError.class,() -> Tile.fromGoogle(0, -1, 1));
		assertThrows(AssertionError.class,() -> Tile.fromGoogle(0, 3, 1));
	}
	
	@Test
	void notAssertGoogleY() {
		assertAll(()-> Tile.fromGoogle(0, 0, 1));
		assertAll(()-> Tile.fromGoogle(0, 2, 1));
	}
	
	@Test
	void assertQuadTree(){
		
		assertThrows(AssertionError.class,() -> Tile.fromQuadTree("A202211"));
		assertThrows(AssertionError.class,() -> Tile.fromQuadTree("4202211"));
	}
	
	@Test
	void notAssertQuadTree() {
		assertAll(()-> Tile.fromQuadTree("0202211"));
		assertAll(()-> Tile.fromQuadTree("3202211"));
	}
}
