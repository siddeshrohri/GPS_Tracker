// Correctness testing for COMP1721 Coursework 1 (Basic Solution)

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class BasicTests {

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

  // Point class

  @Test
  @DisplayName("Time stored and retrieved correctly")
  public void time() {
    assertAll(
      () -> assertThat(p1.getTime(), is(t1)),
      () -> assertThat(p2.getTime(), is(t2))
    );
  }

  @Test
  @DisplayName("Longitude stored and retrieved correctly")
  public void longitude() {
    assertAll(
      () -> assertThat(p1.getLongitude(), closeTo(-1.547720, 0.000001)),
      () -> assertThat(p2.getLongitude(), closeTo(-1.548531, 0.000001))
    );
  }

  @Test
  @DisplayName("Latitude stored and retrieved correctly")
  public void latitude() {
    assertAll(
      () -> assertThat(p1.getLatitude(), closeTo(53.803941, 0.000001)),
      () -> assertThat(p2.getLatitude(), closeTo(53.804616, 0.000001))
    );
  }

  @Test
  @DisplayName("GPSException if longitude is too low")
  public void longitudeTooLow() {
    assertThrows(GPSException.class, () -> new Point(t1, -180.5, 0.0, 0.0));
  }

  @Test
  @DisplayName("GPSException if longitude is too high")
  public void longitudeTooHigh() {
    assertThrows(GPSException.class, () -> new Point(t1, 180.5, 0.0, 0.0));
  }

  @Test
  @DisplayName("GPSException if longitude is too low")
  public void latitudeTooLow() {
    assertThrows(GPSException.class, () -> new Point(t1, 0.0, -90.5, 0.0));
  }

  @Test
  @DisplayName("GPSException if latitude is too high")
  public void latitudeTooHigh() {
    assertThrows(GPSException.class, () -> new Point(t1, 0.0, 90.5, 0.0));
  }

  @Test
  @DisplayName("Elevation stored and retrieved correctly")
  public void elevation() {
    assertAll(
      () -> assertThat(p1.getElevation(), closeTo(69.8, 0.000001)),
      () -> assertThat(p2.getElevation(), closeTo(72.5, 0.000001))
    );
  }

  @Test
  @DisplayName("Correct string representation of a Point")
  public void pointAsString() {
    assertAll(
      () -> assertThat(p1.toString(), is("(-1.54772, 53.80394), 69.8 m")),
      () -> assertThat(p2.toString(), is("(-1.54853, 53.80462), 72.5 m"))
    );
  }

  // Track class

  @Test
  @DisplayName("Size of a Track returned correctly")
  public void trackSize() {
    assertAll(
      () -> assertThat(track1.size(), is(0)),
      () -> assertThat(track2.size(), is(4))
    );
  }

  @Test
  @DisplayName("Points stored in and retrieved from a Track correctly")
  public void getPoint() {
    assertAll(
      () -> assertThat(track2.get(0), is(p1)),
      () -> assertThat(track2.get(1), is(p2)),
      () -> assertThat(track2.get(2), is(p3)),
      () -> assertThat(track2.get(3), is(p4))
    );
  }

  @Test
  @DisplayName("GPSException when retrieving from an empty Track")
  public void getPointEmptyDataset() {
    assertThrows(GPSException.class, () -> track1.get(0));
  }

  @Test
  @DisplayName("GPSException if index is too low")
  public void pointIndexTooLow() {
    assertThrows(GPSException.class, () -> track2.get(-1));
  }

  @Test
  @DisplayName("GPSException if index is too high")
  public void pointIndexTooHigh() {
    assertThrows(GPSException.class, () -> track2.get(4));
  }

  @Test
  @DisplayName("Appropriate exception if data file does not exist")
  public void readFileNotFound() throws IOException {
    Throwable t = assertThrows(IOException.class, () -> track1.readFile("this_file_does_not_exist"));
    assertThat(t.getClass(), anyOf(is(FileNotFoundException.class), is(NoSuchFileException.class)));
  }

  @Test
  @DisplayName("GPSException if data file has fewer than 4 columns")
  public void readMissingData() throws IOException {
    assertThrows(GPSException.class, () -> track1.readFile("data/bad.csv"));
  }

  @Test
  @DisplayName("Points read into a Track correctly")
  public void readFile() throws IOException {
    track1.readFile("data/test.csv");
    assertAll(
      () -> assertThat(track1.size(), is(4)),
      () -> assertThat(track1.get(0).getElevation(), closeTo(69.8, 0.000001)),
      () -> assertThat(track1.get(1).getElevation(), closeTo(72.5, 0.000001))
    );
  }

  @Test
  @DisplayName("Old data cleared when reading a new file")
  public void readClearOldData() throws IOException {
    track2.readFile("data/test.csv");
    assertThat(track2.size(), is(4));
  }
}
