library(ggplot2);

# Análisis según la librería PRCOMP
elements <- read.csv(file.path("C:\\Users\\lucia\\Desktop\\SIA\\europe.csv"))
elements_pca <- prcomp(elements[, 2:8], scale. = TRUE)
countries <- elements[,1]
index <- elements_pca$x[,1]
result.data <- data.frame(countries, index, stringsAsFactors=FALSE)
pca_plot <- ggplot(data = result.data, aes(x = reorder(countries, index), y = index, fill = index))+
  geom_bar(stat = "identity")+
  theme_minimal()+
  scale_fill_gradient(low = "green", high = "red")+
  coord_flip()
pca_plot + labs( title = "First Component Analysis (Library Version)",
                 y = "Index By Country", x = "Country", fill = "Index")

# Análisis según la Regla de Oja
elements_oja <- read.csv(file.path("C:\\Users\\lucia\\Desktop\\SIA\\oja_rule.csv"))
countries_oja <- elements_oja[,1]
index_oja <- elements_oja[,2]
result_oja.data <- data.frame(countries_oja, index_oja, stringsAsFactors=FALSE)
pca_plot_oja <- ggplot(data = result_oja.data, aes(x = reorder(countries_oja, index_oja), y = index_oja, fill = index_oja))+
  geom_bar(stat = "identity")+
  theme_minimal()+
  scale_fill_gradient(low = "green", high = "red")+
  coord_flip()
pca_plot_oja + labs( title = "First Component Analysis (Oja's Rule)",
                 y = "Index By Country", x = "Country", fill = "Index")