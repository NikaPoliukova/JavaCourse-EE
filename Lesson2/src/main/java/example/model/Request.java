package example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Request {
  Long userIdFrom;
  Long userIdTo;
  boolean status;
}
