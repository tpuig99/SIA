library(ggplot2)

co <- read.csv(file.path("data/middle_layer.csv"))

x <- co[,1]
y <- co[,2]
lab <- co[,3]

plot(x, y,
     main= "Plot of the middle layer of Autoencoder",
     xlab= "X Axis",
     ylab= "Y Axis",
     col= "transparent", pch = 19, cex = 1, lty = "solid", lwd = 1)

text(x, y, labels=lab, cex= 1.2, col="orange")
