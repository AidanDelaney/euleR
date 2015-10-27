euleR <- function (combinations, url, ...) {
  if(missing(combinations)) {
    stop("combinations must be specified")
  }
  
  j <- foreach(i = 1:length(combinations)) %do% combinations[i]
  l <- list("area_specifications" = j)
  json <- toJSON(l)
  print(json)
  
  httpheader <- c(Accept="application/json; charset=UTF-8",
                  "Content-Type"="application/json")
  
  if(missing(url)) {
    # if you don't provide a URL we'll use a test one.
    r <- postForm('http://localhost:8080', .opts=list(httpheader=httpheader
                                                      ,postfields=json))
  } else {
    r <- postForm(url, .opts=list(httpheader=httpheader
                                  ,postfields=json))
  }
  print(r)
  fromJSON(r)
}

plot.euleR <- function(d) {
  max_radius <- max(sapply(d$circles, function (x) x$r))
  max_x <- max(sapply(d$circles, function (x) x$xc))
  min_x <- min(sapply(d$circles, function (x) x$xc))
  max_y <- max(sapply(d$circles, function (x) x$yc))
  min_y <- min(sapply(d$circles, function (x) x$yc))
  
  # Create a large enough canvas
  emptyplot(xlim=c(min_x - max_radius, max_x + max_radius), ylim=c(min_y - max_radius, max_y + max_radius))
  
  sapply(d$circles, function (x) filledcircle(r1=x$r, mid=c(x$xc, x$yc), col=rgb(1,1,1,0), lcol="black"))
}

