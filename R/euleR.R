euleR <- function (combinations, url, ...) {
  if(missing(combinations)) {
    stop("combinations must be specified")
  }

  j <- foreach(i = 1:length(combinations)) %do% combinations[i]
  l <- list("area_specifications" = j)
  json <- toJSON(l)
  #print(json)

  if(missing(url)) {
    # if you don't provide a URL we'll use a test one.
    resp <- doFormPost('http://localhost:8080/layout', json)
  } else {
    resp <- doFormPost(url, json)
  }
  structure(fromJSON(resp), class="euleR")
}

doFormPost <- function (url, json) {
  #if(!(url.exists(url, .opts=list(post=1L)))) {
  #  stop(paste("The requested URL cannot be contacted: ", url))
  #}
  httpheader <- c(Accept="application/json; charset=UTF-8",
                  "Content-Type"="application/json")
  response <- postForm(url, .opts=list(httpheader=httpheader
                                       ,postfields=json))
}

plot.euleR <- function(d) {

  if(0 == length(d$circles)) {
       return()
  }
  max_radius <- max(sapply(d$circles, function (x) x$radius))
  max_x <- max(sapply(d$circles, function (x) x$x))
  min_x <- min(sapply(d$circles, function (x) x$x))
  max_y <- max(sapply(d$circles, function (x) x$y))
  min_y <- min(sapply(d$circles, function (x) x$y))

  # Create a large enough canvas
  emptyplot(xlim=c(min_x - max_radius, max_x + max_radius), ylim=c(min_y - max_radius, max_y + max_radius))

  sapply(d$circles, function (x) {
    filledcircle(r1=x$radius, mid=c(x$x, x$y), col=rgb(1,1,1,0), lcol="black")
    text(x$x, x$y, x$label)
    })
}
