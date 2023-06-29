// Class correctness testing for COMP1721 Coursework 1 (Full Solution)

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class FullTests {

  private ZonedDateTime t1, t2, t3, t4;
  private Point p1, p2, p3, p4;
  private Track track1, track2;

  @BeforeEach
  public void perTestSetup() {
    t1 = ZonedDateTime.parse("2022-02-17T09:52:39Z");
    t2 = ZonedDateTime.parse("2022-02-17T09:53:31Z");
    t3 = ZonedDateTime.parse("2022-02-17T09:54:29Z");
    t4 = ZonedDateTime.parse("2022-02-17T09:55:31Z");

    p1 = new Point(t1, -1.547720, 53.803941, 69.8);
    p2 = new Point(t2, -1.548531, 53.804616, 72.5);
    p3 = new Point(t3, -1.549418, 53.805238, 68.1);
    p4 = new Point(t4, -1.550828, 53.805469, 70.5);

    track1 = new Track();

    track2 = new Track();
    track2.add(p1);
    track2.add(p2);
    track2.add(p3);
    track2.add(p4);
  }

  // Track class

  @Test
  @DisplayName("Lowest point of a Track found correctly")
  public void lowestPoint() {
    assertThat(track2.lowestPoint(), is(p3));
  }

  @Test
  @DisplayName("GPSException if seeking lowest point in an empty Track")
  public void lowestPointNotEnoughData() {
    assertThrows(GPSException.class, () -> track1.lowestPoint());
  }

  @Test
  @DisplayName("Highest point of a Track found correctly")
  public void highestPoint() {
    assertThat(track2.highestPoint(), is(p2));
  }

  @Test
  @DisplayName("GPSException if seeking highest point in an empty Track")
  public void highestPointNotEnoughData() {
    assertThrows(GPSException.class, () -> track1.highestPoint());
  }

  @Test
  @DisplayName("Total distance computed correctly")
  public void totalDistance() {
    assertThat(track2.totalDistance(), closeTo(278.53495, 0.00001));
  }

  @Test
  @DisplayName("GPSException if fewer than 2 points when computing distance")
  public void totalDistanceNotEnoughData() {
    Track t = new Track();
    t.add(p1);
    assertThrows(GPSException.class, () -> t.totalDistance());
  }

  @Test
  @DisplayName("Average speed computed correctly")
  public void averageSpeed() {
    assertThat(track2.averageSpeed(), closeTo(1.61939, 0.00001));
  }

  @Test
  @DisplayName("GPSException if fewer than 2 points when computing average speed")
  public void averageSpeedNotEnoughData() {
    Track t = new Track();
    t.add(p1);
    assertThrows(GPSException.class, () -> t.averageSpeed());
  }
}
