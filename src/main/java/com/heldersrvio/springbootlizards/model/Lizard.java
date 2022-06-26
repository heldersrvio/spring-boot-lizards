package com.heldersrvio.springbootlizards.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "lizards")
public class Lizard {
  private String id;
  private String species;
  private String commonName;
  private String picture;

  public Lizard() { }

  public Lizard(String id, String species, String commonName, String picture) {
    this.id = id;
    this.species = species;
    this.commonName = commonName;
    this.picture = picture;
  }

  @DynamoDBHashKey
  @DynamoDBAutoGeneratedKey
  public String getId() {
    return id;
  }

  @DynamoDBAttribute
  public String getSpecies() {
    return species;
  }
  @DynamoDBAttribute
  public String getCommonName() {
    return commonName;
  }
  @DynamoDBAttribute
  public String getPicture() {
    return picture;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }
}