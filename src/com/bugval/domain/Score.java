package com.bugval.domain;

import com.google.gson.annotations.SerializedName;
import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.math.BigInteger;

@Gson.TypeAdapters
@Value.Immutable
public interface Score {
  String getId();

  BigInteger getScore();

  @SerializedName("pos")
  int getPosition();
}
