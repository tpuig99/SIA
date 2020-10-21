library(reshape2)
library(ggplot2)
library(treemapify)
library(matlab)

distance_matrix <- as.matrix(read.csv(file.path("data/distance_matrix.csv"), header=FALSE))

longData<-melt(distance_matrix)

ggplot(longData, aes(x = Var2, y = Var1)) +
  geom_tile(aes(fill=value, width=0.9, height=0.9)) +
  scale_fill_gradient(low="#FFFFFF", high="#000000") +
  labs(x="Column", y="Row", title="Distance Matrix") +
  theme_minimal()

registers_matrix <- as.matrix(read.csv(file.path("data/registers_matrix.csv"), header=FALSE))

longData2<-melt(registers_matrix)

ggplot(longData2, aes(x = Var2, y = Var1)) +
  geom_tile(aes(fill=value, width=0.9, height=0.9)) +
  scale_fill_gradient(low="green", high="red") +
  labs(x="Column", y="Row", title="Registers Matrix") +
  theme_minimal()

countries_and_neurons <- read.csv(file.path("data/countries_and_neurons.csv"))
country <- countries_and_neurons[,1]
neuron <- countries_and_neurons[,2]
aux <- ones(length(c1))[,1]
countries_and_neurons.data <- data.frame(country, neuron, aux, stringsAsFactors=FALSE)

ggplot(countries_and_neurons.data, aes(area = aux, fill = neuron, label = country,
                subgroup = neuron)) +
  geom_treemap() +
  geom_treemap_subgroup_border(colour = "red", size = 2) +
  geom_treemap_text(colour = "white", place = "centre", reflow = TRUE, grow=TRUE) +
  geom_treemap_subgroup_text(place = "centre", grow = T, alpha = 0.5, colour =
                             "black", fontface = "italic", min.size = 0)

