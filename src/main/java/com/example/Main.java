package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
    // Constants for styling
    private static final class Styles {
        // Colors
        static final class Colors {
            static final String GRID_CELL_BACKGROUND = "#E0E0E0";
            static final String GRID_CELL_HOVER = "#D0D0D0";
            static final String GRID_CELL_BORDER = "#C0C0C0";
            static final String PREVIEW_BACKGROUND = "#F0F0F0";
            static final String PREVIEW_BORDER = "#E0E0E0";
            static final String SELECTED_BORDER = "#1480F2";
            static final String PLACEHOLDER_BACKGROUND = "#D0D0D0";
            static final String PLACEHOLDER_BORDER = "#C0C0C0";
            static final String PLACEHOLDER_HOVER = "#C8C8C8";
            static final String TEXT_COLOR = "#333333";
            static final String DESC_COLOR = "#666666";
            static final String PRICE_COLOR = "#000000";
        }

        // Dimensions
        static final class Dimensions {
            static final int CELL_SIZE = 180;
            static final int IMAGE_SIZE = 140;
            static final int CELL_PADDING = 5;
            static final int BORDER_WIDTH = 1;
            static final int BORDER_RADIUS = 5;
            static final int GRID_PADDING = 20;
            static final int GRID_GAP = 20;
            static final int PREVIEW_WIDTH = 300;
            static final int PREVIEW_IMAGE_SIZE = 250;
            static final int PREVIEW_PADDING = 20;
            static final int PREVIEW_INFO_PADDING = 15;
            static final int CELL_CONTENT_PADDING = 8;
        }

        // Font sizes
        static final class FontSizes {
            static final int TITLE = 16;
            static final int DESC = 12;
            static final int BRAND = 11;
            static final int PRICE = 13;
        }

        // Effects
        static final class Effects {
            static final double SHADOW_RADIUS = 3.0;
            static final double SHADOW_OFFSET = 2.0;
            static final double SHADOW_OPACITY = 0.2;
            static final double HOVER_SCALE = 1.05;
        }
    }

    // Product data model
    private static final class Product {
        final String name;
        final String description;
        final String brand;
        final String price;

        Product(String[] data) {
            this.name = data[0];
            this.description = data[1];
            this.brand = data[2];
            this.price = data[3];
        }
    }

    // Product data
    private static final class ProductData {
        static final Product[] PRODUCTS = {
            new Product(new String[]{"ADIDAS ULTRABOOST 23", "Màn hình 6.1 inch, Chip A17 Pro", "Adidas", "$120"}),
            new Product(new String[]{"ADIDAS SUPERSTAR ORIGINALS", "Giày chạy bộ hiệu suất cao với công nghệ Boost mang lại cảm giác êm ái và đàn hồi tối đa.", "Adidas", "$140"}),
            new Product(new String[]{"ADIDAS PREDATOR EDGE.1 FG", "Thiết kế cổ điển với mũi giày vỏ sò huyền thoại, phù hợp cho cả thời trang đường phố và hàng ngày.", "Adidas", "$100"}),
            new Product(new String[]{"ADIDAS FORUM LOW", "Giày đá bóng dành cho sân cỏ tự nhiên, hỗ trợ kiểm soát bóng vượt trội nhờ thiết kế mặt vân nổi.", "Adidas", "$120"}),
            new Product(new String[]{"ADIDAS TERREX SWIFT R3 GTX", "Sneaker phong cách retro từ thập niên 80, kết hợp hoàn hảo giữa chất liệu da và kiểu dáng hiện đại.", "Adidas", "$170"}),
            new Product(new String[]{"ADIDAS NMD_R1 V2", "Giày leo núi chống nước với đế Continental giúp bám chắc trên nhiều địa hình khác nhau.", "Adidas", "$140"}),
            new Product(new String[]{"NIEK", "Thiết kế thời trang với phần đế Boost và kiểu dáng năng động, phù hợp để mang hàng ngày.", "NIKE", "$220"}),
            new Product(new String[]{"LOC GIO BANG THAN", "Giày 6 màu", "Rainbow", "$0"})
        };
    }

    // Grid configuration
    private static final int GRID_ROWS = 2;
    private static final int GRID_COLS = 4;
    private static final String IMAGE_PATH_TEMPLATE = "/images/img%d.png";

    // UI Components
    private ImageView previewImageView;
    private VBox previewInfoBox;
    private StackPane selectedCell;

    @Override
    public void start(Stage stage) {
        HBox root = createRootLayout();
        GridPane grid = createGrid();
        VBox previewBox = createPreviewBox();
        
        root.getChildren().addAll(previewBox, grid);
        addProductsToGrid(grid);
        showStage(stage, root);
    }

    private HBox createRootLayout() {
        HBox root = new HBox(Styles.Dimensions.GRID_PADDING);
        root.setPadding(new Insets(Styles.Dimensions.GRID_PADDING));
        root.setAlignment(Pos.CENTER);
        return root;
    }

    private VBox createPreviewBox() {
        VBox previewBox = new VBox(Styles.Dimensions.PREVIEW_PADDING);
        previewBox.setPrefWidth(Styles.Dimensions.PREVIEW_WIDTH);
        previewBox.setAlignment(Pos.TOP_CENTER);
        
        previewImageView = createPreviewImageView();
        previewInfoBox = createPreviewInfoBox();
        
        applyPreviewBoxStyle(previewBox);
        previewBox.getChildren().addAll(previewImageView, previewInfoBox);
        
        return previewBox;
    }

    private ImageView createPreviewImageView() {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(Styles.Dimensions.PREVIEW_IMAGE_SIZE);
        imageView.setFitHeight(Styles.Dimensions.PREVIEW_IMAGE_SIZE);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private VBox createPreviewInfoBox() {
        VBox infoBox = new VBox(Styles.Dimensions.PREVIEW_INFO_PADDING);
        infoBox.setAlignment(Pos.TOP_LEFT);
        infoBox.setMaxWidth(Styles.Dimensions.PREVIEW_WIDTH - 2 * Styles.Dimensions.PREVIEW_PADDING);
        return infoBox;
    }

    private void applyPreviewBoxStyle(VBox previewBox) {
        String style = String.format(
            "-fx-background-color: %s;" +
            "-fx-padding: %dpx;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: %dpx;" +
            "-fx-border-radius: %dpx;" +
            "-fx-background-radius: %dpx;",
            Styles.Colors.PREVIEW_BACKGROUND,
            Styles.Dimensions.PREVIEW_PADDING,
            Styles.Colors.PREVIEW_BORDER,
            Styles.Dimensions.BORDER_WIDTH,
            Styles.Dimensions.BORDER_RADIUS,
            Styles.Dimensions.BORDER_RADIUS
        );
        previewBox.setStyle(style);
        previewBox.setEffect(createShadow());
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(Styles.Dimensions.GRID_PADDING));
        grid.setHgap(Styles.Dimensions.GRID_GAP);
        grid.setVgap(Styles.Dimensions.GRID_GAP);
        return grid;
    }

    private void addProductsToGrid(GridPane grid) {
        DropShadow shadow = createShadow();
        
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int index = row * GRID_COLS + col;
                if (index < ProductData.PRODUCTS.length) {
                    StackPane cell = createProductCell(ProductData.PRODUCTS[index], shadow);
                    grid.add(cell, col, row);
                }
            }
        }
    }

    private StackPane createProductCell(Product product, DropShadow shadow) {
        StackPane cell = new StackPane();
        cell.setPrefSize(Styles.Dimensions.CELL_SIZE, Styles.Dimensions.CELL_SIZE);
        cell.setMaxSize(Styles.Dimensions.CELL_SIZE, Styles.Dimensions.CELL_SIZE);
        
        VBox contentBox = createProductContent(product);
        cell.getChildren().add(contentBox);
        
        applyCellStyle(cell);
        cell.setEffect(shadow);
        
        addHoverEffects(cell);
        addClickHandler(cell, product);

        // Tải ảnh ngay khi tạo cell
        ImageView imageView = (ImageView) contentBox.getChildren().get(2); // Lấy ImageView từ contentBox
        Image image = loadProductImage(product);
        if (image != null) {
            imageView.setImage(image);
        } else {
            // Áp dụng style placeholder cho image view
            imageView.setStyle(String.format(
                "-fx-background-color: %s;" +
                "-fx-border-color: %s;" +
                "-fx-border-width: %dpx;" +
                "-fx-border-radius: %dpx;" +
                "-fx-background-radius: %dpx;",
                Styles.Colors.PLACEHOLDER_BACKGROUND,
                Styles.Colors.PLACEHOLDER_BORDER,
                Styles.Dimensions.BORDER_WIDTH,
                Styles.Dimensions.BORDER_RADIUS,
                Styles.Dimensions.BORDER_RADIUS
            ));
        }
        
        return cell;
    }

    private VBox createProductContent(Product product) {
        VBox contentBox = new VBox(Styles.Dimensions.CELL_CONTENT_PADDING);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setMaxWidth(Styles.Dimensions.CELL_SIZE - 2 * Styles.Dimensions.CELL_PADDING);
        
        Label titleLabel = createLabel(product.name, 
            Font.font("System", FontWeight.BOLD, Styles.FontSizes.TITLE));
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label descLabel = createLabel(product.description, 
            Font.font("System", Styles.FontSizes.DESC));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        
        ImageView imageView = createProductImageView();
        HBox bottomBox = createProductBottomBox(product);
        
        contentBox.getChildren().addAll(titleLabel, descLabel, imageView, bottomBox);
        return contentBox;
    }

    private ImageView createProductImageView() {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(Styles.Dimensions.IMAGE_SIZE);
        imageView.setFitHeight(Styles.Dimensions.IMAGE_SIZE);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private HBox createProductBottomBox(Product product) {
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setSpacing(Styles.Dimensions.CELL_CONTENT_PADDING);
        HBox.setHgrow(bottomBox, javafx.scene.layout.Priority.ALWAYS);
        bottomBox.setPadding(new Insets(0));
        
        Label brandLabel = createLabel(product.brand, 
            Font.font("System", Styles.FontSizes.BRAND));
        
        Label priceLabel = createLabel(product.price, 
            Font.font("System", FontWeight.BOLD, Styles.FontSizes.PRICE));
        priceLabel.setTextFill(Color.web(Styles.Colors.PRICE_COLOR));
        HBox.setHgrow(priceLabel, javafx.scene.layout.Priority.ALWAYS);
        priceLabel.setAlignment(Pos.CENTER_RIGHT);
        priceLabel.setPadding(new Insets(0));
        priceLabel.setMaxWidth(Double.MAX_VALUE);
        
        bottomBox.getChildren().addAll(brandLabel, priceLabel);
        return bottomBox;
    }

    private void applyCellStyle(StackPane cell) {
        String style = String.format(
            "-fx-background-color: %s;" +
            "-fx-padding: %dpx;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: %dpx;" +
            "-fx-border-radius: %dpx;" +
            "-fx-background-radius: %dpx;",
            Styles.Colors.GRID_CELL_BACKGROUND,
            Styles.Dimensions.CELL_PADDING,
            Styles.Colors.GRID_CELL_BORDER,
            Styles.Dimensions.BORDER_WIDTH,
            Styles.Dimensions.BORDER_RADIUS,
            Styles.Dimensions.BORDER_RADIUS
        );
        cell.setStyle(style);
    }

    private Label createLabel(String text, Font font) {
        Label label = new Label(text);
        label.setFont(font);
        if (font.getSize() == Styles.FontSizes.DESC) {
            label.setTextFill(Color.web(Styles.Colors.DESC_COLOR));
        } else {
            label.setTextFill(Color.web(Styles.Colors.TEXT_COLOR));
        }
        return label;
    }

    private DropShadow createShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(Styles.Effects.SHADOW_RADIUS);
        shadow.setOffsetX(Styles.Effects.SHADOW_OFFSET);
        shadow.setOffsetY(Styles.Effects.SHADOW_OFFSET);
        shadow.setColor(Color.rgb(0, 0, 0, Styles.Effects.SHADOW_OPACITY));
        return shadow;
    }

    private void addHoverEffects(StackPane cell) {
        String baseStyle = cell.getStyle();
        
        cell.setOnMouseEntered(e -> {
            if (cell != selectedCell) {
                cell.setStyle(baseStyle + 
                    String.format("-fx-background-color: %s;" +
                                "-fx-border-color: %s;" +
                                "-fx-cursor: hand;",
                                Styles.Colors.GRID_CELL_HOVER,
                                Styles.Colors.GRID_CELL_BORDER));
                cell.setScaleX(Styles.Effects.HOVER_SCALE);
                cell.setScaleY(Styles.Effects.HOVER_SCALE);
            }
        });

        cell.setOnMouseExited(e -> {
            if (cell != selectedCell) {
                cell.setStyle(baseStyle);
                cell.setScaleX(1.0);
                cell.setScaleY(1.0);
            }
        });
    }

    private void addClickHandler(StackPane cell, Product product) {
        cell.setOnMouseClicked(e -> {
            if (selectedCell != null) {
                String baseStyle = selectedCell.getStyle().replace(
                    String.format("-fx-border-color: %s;", Styles.Colors.SELECTED_BORDER),
                    String.format("-fx-border-color: %s;", Styles.Colors.GRID_CELL_BORDER)
                );
                selectedCell.setStyle(baseStyle);
            }

            selectedCell = cell;
            String newStyle = cell.getStyle().replace(
                String.format("-fx-border-color: %s;", Styles.Colors.GRID_CELL_BORDER),
                String.format("-fx-border-color: %s;", Styles.Colors.SELECTED_BORDER)
            );
            cell.setStyle(newStyle);

            updatePreview(product);
        });
    }

    private void updatePreview(Product product) {
        Image image = loadProductImage(product);
        if (image != null) {
            previewImageView.setImage(image);
            updatePreviewInfo(product);
        } else {
            System.out.println("Không thể cập nhật preview vì không tải được ảnh cho sản phẩm: " + product.name);
            // Áp dụng style placeholder cho preview image
            previewImageView.setImage(null);
            previewImageView.setStyle(String.format(
                "-fx-background-color: %s;" +
                "-fx-border-color: %s;" +
                "-fx-border-width: %dpx;" +
                "-fx-border-radius: %dpx;" +
                "-fx-background-radius: %dpx;",
                Styles.Colors.PLACEHOLDER_BACKGROUND,
                Styles.Colors.PLACEHOLDER_BORDER,
                Styles.Dimensions.BORDER_WIDTH,
                Styles.Dimensions.BORDER_RADIUS,
                Styles.Dimensions.BORDER_RADIUS
            ));
        }
    }

    private Image loadProductImage(Product product) {
        int index = getProductIndex(product);
        if (index == -1) {
            System.out.println("Không tìm thấy index cho sản phẩm: " + product.name);
            return null;
        }

        String imagePath = String.format(IMAGE_PATH_TEMPLATE, index + 1);
        System.out.println("Đang tải ảnh từ đường dẫn: " + imagePath);
        
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            if (image.isError()) {
                System.out.println("Lỗi khi tải ảnh: " + imagePath);
                return null;
            }
            System.out.println("Tải ảnh thành công: " + imagePath);
            return image;
        } catch (Exception e) {
            System.out.println("Lỗi khi tải ảnh " + imagePath + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private int getProductIndex(Product product) {
        for (int i = 0; i < ProductData.PRODUCTS.length; i++) {
            if (ProductData.PRODUCTS[i] == product) {
                return i;
            }
        }
        return -1;
    }

    private void updatePreviewInfo(Product product) {
        previewInfoBox.getChildren().clear();
        
        Label titleLabel = createLabel(product.name, 
            Font.font("System", FontWeight.BOLD, Styles.FontSizes.TITLE));
        titleLabel.setWrapText(true);
        
        Label descLabel = createLabel(product.description, 
            Font.font("System", Styles.FontSizes.DESC));
        descLabel.setWrapText(true);
        
        Label brandLabel = createLabel("Hãng: " + product.brand, 
            Font.font("System", Styles.FontSizes.BRAND));
        
        Label priceLabel = createLabel(product.price, 
            Font.font("System", FontWeight.BOLD, Styles.FontSizes.PRICE));
        priceLabel.setTextFill(Color.web(Styles.Colors.PRICE_COLOR));
        
        previewInfoBox.getChildren().addAll(titleLabel, descLabel, brandLabel, priceLabel);
    }

    private void showStage(Stage stage, HBox root) {
        Scene scene = new Scene(root);
        stage.setTitle("Grid Images Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
} 