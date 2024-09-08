package at.aau.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ListUtilsTest {

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Nested
  class UnmodifiableList {
    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnEmptyList_whenListIsNullOrEmpty(List<?> list) {
      assertThat(ListUtils.unmodifiableList(list)).isEmpty();
    }

    @Test
    void shouldReturnList_whenListIsNotEmpty() {
      assertThat(ListUtils.unmodifiableList(List.of(1))).isEqualTo(List.of(1));
    }

    @Test
    void shouldThrowException_whenReturnedListIsModified() {
      List<Integer> list = new ArrayList<>();
      list.add(1);
      List<Integer> numbers = ListUtils.unmodifiableList(list);

      assertThrows(UnsupportedOperationException.class, () -> numbers.add(2));
    }
  }

  @Nested
  class NotNullOrEmpty {
    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnFalse_whenListIsNullOrEmpty(List<?> list) {
      assertThat(ListUtils.notNullOrEmpty(list)).isFalse();
    }

    @Test
    void shouldReturnTrue_whenListIsNotNullOrEmpty() {
      assertThat(ListUtils.notNullOrEmpty(List.of(1))).isTrue();
    }
  }
}
