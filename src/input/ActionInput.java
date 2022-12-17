/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package input;

public final class ActionInput {
  private String type;
  private String page;
  private String feature;
  private CredentialsInput credentials;
  private String startsWith;
  private FiltersInput filters;
  private int count;
  private String movie;
  private String objectType;
  private int rate;

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getPage() {
    return page;
  }

  public void setPage(final String page) {
    this.page = page;
  }

  public String getFeature() {
    return feature;
  }

  public void setFeature(final String feature) {
    this.feature = feature;
  }

  public CredentialsInput getCredentials() {
    return credentials;
  }

  public void setCredentials(final CredentialsInput credentials) {
    this.credentials = credentials;
  }

  public String getStartsWith() {
    return startsWith;
  }

  public void setStartsWith(final String startsWith) {
    this.startsWith = startsWith;
  }

  public FiltersInput getFilters() {
    return filters;
  }

  public void setFilters(final FiltersInput filters) {
    this.filters = filters;
  }

  public int getCount() {
    return count;
  }

  public void setCount(final int count) {
    this.count = count;
  }

  public String getMovie() {
    return movie;
  }

  public void setMovie(final String movie) {
    this.movie = movie;
  }

  public String getObjectType() {
    return objectType;
  }

  public void setObjectType(final String objectType) {
    this.objectType = objectType;
  }

  public int getRate() {
    return rate;
  }

  public void setRate(final int rate) {
    this.rate = rate;
  }
}
