package org.assertj.core.util.introspection;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.recursive.comparison.ComparingSnakeOrCamelCaseFields.COMPARING_SNAKE_OR_CAMEL_CASE_FIELDS;
import static org.assertj.core.util.Preconditions.checkArgument;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Looks up fields by comparing their name previously converted into a common case style.
 */
class CaseStyleInsensitiveFieldLookupStrategy implements FieldLookupStrategy {

  @Override
  public Field findByName(Class<?> acls, String fieldName) throws NoSuchFieldException {
    List<Field> matchingFields = Arrays.stream(acls.getDeclaredFields())
                                       .filter(field -> COMPARING_SNAKE_OR_CAMEL_CASE_FIELDS.normalizeFieldName(field.getName())
                                                                                            .equals(fieldName))
                                       .collect(toList());
    if (matchingFields.isEmpty()) {
      throw new NoSuchFieldException(fieldName);
    }
    checkArgument(matchingFields.size() == 1,
                  "Reference to field %s is ambiguous relative to %s; possible candidates are: %s",
                  fieldName, acls, matchingFields.stream().map(Field::getName).collect(joining(", ")));
    return matchingFields.get(0);
  }

  @Override
  public String getDescription() {
    return "field match even when defined with names in different case styles";
  }
}
