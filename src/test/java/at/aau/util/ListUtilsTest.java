package at.aau.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListUtilsTest {

  @Test
  public void shouldReturnEmptyList_whenListIsNullOrEmpty() {
    assertThat(ListUtils.unmodifiableList(null)).isEmpty();
    assertThat(ListUtils.unmodifiableList(new ArrayList<>())).isEmpty();
  }

  @Test
  public void shouldReturnList_whenListIsNotEmpty() {
    assertThat(ListUtils.unmodifiableList(List.of(1))).isEqualTo(List.of(1));
  }

  @Test
  public void shouldThrowException_whenReturnedListIsModified() {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    List<Integer> numbers = ListUtils.unmodifiableList(list);

    assertThrows(UnsupportedOperationException.class, () -> numbers.add(2));
  }

  @Test
  public void shouldReturnFalse_whenListIsNullOrEmpty() {
    assertThat(ListUtils.notNullOrEmpty(null)).isFalse();
    assertThat(ListUtils.notNullOrEmpty(new ArrayList<>())).isFalse();
  }

  @Test
  public void shouldReturnTrue_whenListIsNotNullOrEmpty() {
    assertThat(ListUtils.notNullOrEmpty(List.of(1))).isTrue();
  }

}
