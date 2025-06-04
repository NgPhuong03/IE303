package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
    // Constants for styling
    private static final class Styles {
        // Colors
        static final String GRID_CELL_BACKGROUND = "#E0E0E0";  // Màu xám cho cell
        static final String GRID_CELL_HOVER = "#D0D0D0";       // Màu xám đậm hơn khi hover
        static final String GRID_CELL_BORDER = "#C0C0C0";      // Màu viền cell
        static final String PREVIEW_BACKGROUND = "#F0F0F0";    // Màu nền khung xem trước
        static final String PREVIEW_BORDER = "#E0E0E0";        // Màu viền khung xem trước
        static final String SELECTED_BORDER = "#4CAF50";       // Màu viền khi được chọn
        static final String PLACEHOLDER_BACKGROUND = "#D0D0D0"; // Màu nền placeholder
        static final String PLACEHOLDER_BORDER = "#C0C0C0";    // Màu viền placeholder
        static final String PLACEHOLDER_HOVER = "#C8C8C8";     // Màu hover placeholder
        static final String TEXT_COLOR = "#333333";            // Màu chữ mặc định
        static final String PRICE_COLOR = "#E53935";           // Màu chữ giá tiền
        
        // Dimensions
        static final int CELL_SIZE = 150;          // Kích thước cell
        static final int IMAGE_SIZE = 140;         // Kích thước ảnh
        static final int CELL_PADDING = 5;         // Padding của cell
        static final int BORDER_WIDTH = 1;
        static final int BORDER_RADIUS = 5;
        static final int GRID_PADDING = 20;
        static final int GRID_GAP = 20;
        static final int PREVIEW_SIZE = 400;
        static final int PREVIEW_PADDING = 20;
        static final int CELL_CONTENT_PADDING = 8; // Padding cho nội dung trong cell
        
        // Font sizes
        static final int TITLE_FONT_SIZE = 14;
        static final int DESC_FONT_SIZE = 12;
        static final int BRAND_FONT_SIZE = 11;
        static final int PRICE_FONT_SIZE = 13;
        
        // Effects
        static final double SHADOW_RADIUS = 3.0;
        static final double SHADOW_OFFSET = 2.0;
        static final double SHADOW_OPACITY = 0.2;
        static final double HOVER_SCALE = 1.05;
        
        // Preview styles
        static final int PREVIEW_WIDTH = 300;          // Chiều rộng khung preview
        static final int PREVIEW_IMAGE_SIZE = 250;     // Kích thước ảnh preview
        static final int PREVIEW_INFO_PADDING = 15;    // Padding cho phần thông tin
        static final int PREVIEW_TITLE_FONT_SIZE = 18; // Font size cho tiêu đề preview
        static final int PREVIEW_DESC_FONT_SIZE = 14;  // Font size cho mô tả preview
        static final int PREVIEW_BRAND_FONT_SIZE = 14; // Font size cho hãng preview
        static final int PREVIEW_PRICE_FONT_SIZE = 16; // Font size cho giá preview
    }

    // Grid configuration
    private static final int GRID_ROWS = 2;
    private static final int GRID_COLS = 4;
    private static final String IMAGE_PATH_TEMPLATE = "/images/img%d.png";

    // Sample product data
    private static final class ProductData {
        static final String[][] PRODUCTS = {
            {"iPhone 15 Pro", "Màn hình 6.1 inch, Chip A17 Pro", "Apple", "29.990.000đ"},
            {"Samsung S24", "Màn hình 6.2 inch, Snapdragon 8 Gen 3", "Samsung", "24.990.000đ"},
            {"Xiaomi 14", "Màn hình 6.36 inch, Snapdragon 8 Gen 3", "Xiaomi", "19.990.000đ"},
            {"Google Pixel 8", "Màn hình 6.2 inch, Tensor G3", "Google", "18.990.000đ"},
            {"OnePlus 12", "Màn hình 6.82 inch, Snapdragon 8 Gen 3", "OnePlus", "17.990.000đ"},
            {"Nothing Phone 2", "Màn hình 6.7 inch, Snapdragon 8+ Gen 1", "Nothing", "14.990.000đ"},
            {"ASUS ROG Phone 8", "Màn hình 6.78 inch, Snapdragon 8 Gen 3", "ASUS", "22.990.000đ"},
            {"Sony Xperia 1 VI", "Màn hình 6.5 inch, Snapdragon 8 Gen 3", "Sony", "25.990.000đ"}
        };
    }

    private ImageView previewImageView;
    private VBox previewInfoBox;
    private StackPane selectedCell;

    @Override
    public void start(Stage stage) {
        HBox root = createRootLayout();
        GridPane grid = createGrid();
        VBox previewBox = createPreviewBox();
        
        root.getChildren().addAll(previewBox, grid);
        addImagesToGrid(grid);
        showStage(stage, root);
    }

    private HBox createRootLayout() {
        HBox root = new HBox(Styles.GRID_PADDING);
        root.setPadding(new Insets(Styles.GRID_PADDING));
        root.setAlignment(Pos.CENTER);
        return root;
    }

    private VBox createPreviewBox() {
        VBox previewBox = new VBox(Styles.PREVIEW_PADDING);
        previewBox.setPrefWidth(Styles.PREVIEW_WIDTH);
        previewBox.setAlignment(Pos.TOP_CENTER);
        
        // Tạo ImageView cho ảnh preview
        previewImageView = new ImageView();
        previewImageView.setFitWidth(Styles.PREVIEW_IMAGE_SIZE);
        previewImageView.setFitHeight(Styles.PREVIEW_IMAGE_SIZE);
        previewImageView.setPreserveRatio(true);
        
        // Tạo VBox cho thông tin sản phẩm
        previewInfoBox = new VBox(Styles.PREVIEW_INFO_PADDING);
        previewInfoBox.setAlignment(Pos.TOP_LEFT);
        previewInfoBox.setMaxWidth(Styles.PREVIEW_WIDTH - 2 * Styles.PREVIEW_PADDING);
        
        // Áp dụng style cho preview box
        String previewStyle = String.format(
            "-fx-background-color: %s;" +
            "-fx-padding: %dpx;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: %dpx;" +
            "-fx-border-radius: %dpx;" +
            "-fx-background-radius: %dpx;",
            Styles.PREVIEW_BACKGROUND,
            Styles.PREVIEW_PADDING,
            Styles.PREVIEW_BORDER,
            Styles.BORDER_WIDTH,
            Styles.BORDER_RADIUS,
            Styles.BORDER_RADIUS
        );
        previewBox.setStyle(previewStyle);
        previewBox.setEffect(createShadow());
        
        // Thêm các thành phần vào preview box
        previewBox.getChildren().addAll(previewImageView, previewInfoBox);
        
        return previewBox;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(Styles.GRID_PADDING));
        grid.setHgap(Styles.GRID_GAP);
        grid.setVgap(Styles.GRID_GAP);
        return grid;
    }

    private void addImagesToGrid(GridPane grid) {
        DropShadow shadow = createShadow();
        
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                StackPane cell = createCell(row, col, shadow);
                grid.add(cell, col, row);
            }
        }
    }

    private DropShadow createShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(Styles.SHADOW_RADIUS);
        shadow.setOffsetX(Styles.SHADOW_OFFSET);
        shadow.setOffsetY(Styles.SHADOW_OFFSET);
        shadow.setColor(Color.rgb(0, 0, 0, Styles.SHADOW_OPACITY));
        return shadow;
    }

    private StackPane createCell(int row, int col, DropShadow shadow) {
        // Tạo container cho cell
        StackPane cell = new StackPane();
        cell.setPrefSize(Styles.CELL_SIZE, Styles.CELL_SIZE);
        cell.setMaxSize(Styles.CELL_SIZE, Styles.CELL_SIZE);
        
        // Tạo VBox để chứa tất cả nội dung
        VBox contentBox = new VBox(Styles.CELL_CONTENT_PADDING);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setMaxWidth(Styles.CELL_SIZE - 2 * Styles.CELL_PADDING);
        
        // Tạo các label cho thông tin sản phẩm
        Label titleLabel = createLabel(ProductData.PRODUCTS[row * GRID_COLS + col][0], 
            Font.font("System", FontWeight.BOLD, Styles.TITLE_FONT_SIZE));
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label descLabel = createLabel(ProductData.PRODUCTS[row * GRID_COLS + col][1], 
            Font.font("System", Styles.DESC_FONT_SIZE));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Tạo HBox cho brand và price
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setSpacing(Styles.CELL_CONTENT_PADDING);
        
        Label brandLabel = createLabel(ProductData.PRODUCTS[row * GRID_COLS + col][2], 
            Font.font("System", Styles.BRAND_FONT_SIZE));
        
        Label priceLabel = createLabel(ProductData.PRODUCTS[row * GRID_COLS + col][3], 
            Font.font("System", FontWeight.BOLD, Styles.PRICE_FONT_SIZE));
        priceLabel.setTextFill(Color.web(Styles.PRICE_COLOR));
        
        // Thêm brand và price vào bottomBox
        bottomBox.getChildren().addAll(brandLabel, priceLabel);
        
        // Tạo ImageView cho ảnh
        ImageView imageView = new ImageView();
        imageView.setFitWidth(Styles.IMAGE_SIZE);
        imageView.setFitHeight(Styles.IMAGE_SIZE);
        imageView.setPreserveRatio(true);
        
        // Thêm tất cả các thành phần vào contentBox
        contentBox.getChildren().addAll(titleLabel, imageView, descLabel, bottomBox);
        
        // Thêm contentBox vào cell
        cell.getChildren().add(contentBox);
        
        // Áp dụng style cho cell
        String baseStyle = String.format(
            "-fx-background-color: %s;" +
            "-fx-padding: %dpx;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: %dpx;" +
            "-fx-border-radius: %dpx;" +
            "-fx-background-radius: %dpx;",
            Styles.GRID_CELL_BACKGROUND,
            Styles.CELL_PADDING,
            Styles.GRID_CELL_BORDER,
            Styles.BORDER_WIDTH,
            Styles.BORDER_RADIUS,
            Styles.BORDER_RADIUS
        );
        cell.setStyle(baseStyle);
        cell.setEffect(shadow);

        // Thêm hiệu ứng hover
        addHoverEffects(cell);
        
        // Thêm xử lý click
        addClickHandler(cell, imageView, row, col);
        
        // Tải ảnh
        loadImage(imageView, row, col);
        
        return cell;
    }

    private Label createLabel(String text, Font font) {
        Label label = new Label(text);
        label.setFont(font);
        label.setTextFill(Color.web(Styles.TEXT_COLOR));
        return label;
    }

    private void addHoverEffects(StackPane cell) {
        String baseStyle = cell.getStyle();
        
        cell.setOnMouseEntered(e -> {
            if (cell != selectedCell) {
                cell.setStyle(baseStyle + 
                    String.format("-fx-background-color: %s;" +
                                "-fx-border-color: %s;" +
                                "-fx-cursor: hand;",
                                Styles.GRID_CELL_HOVER,
                                Styles.GRID_CELL_BORDER));
                cell.setScaleX(Styles.HOVER_SCALE);
                cell.setScaleY(Styles.HOVER_SCALE);
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

    private void addClickHandler(StackPane cell, ImageView imageView, int row, int col) {
        cell.setOnMouseClicked(e -> {
            // Reset style of previously selected cell
            if (selectedCell != null) {
                String baseStyle = selectedCell.getStyle().replace(
                    String.format("-fx-border-color: %s;", Styles.SELECTED_BORDER),
                    String.format("-fx-border-color: %s;", Styles.GRID_CELL_BORDER)
                );
                selectedCell.setStyle(baseStyle);
            }

            // Update selected cell
            selectedCell = cell;
            String newStyle = cell.getStyle().replace(
                String.format("-fx-border-color: %s;", Styles.GRID_CELL_BORDER),
                String.format("-fx-border-color: %s;", Styles.SELECTED_BORDER)
            );
            cell.setStyle(newStyle);

            // Update preview
            if (imageView.getImage() != null) {
                previewImageView.setImage(imageView.getImage());
                updatePreviewInfo(row, col);
            }
        });
    }

    private void loadImage(ImageView imageView, int row, int col) {
        String imagePath = String.format(IMAGE_PATH_TEMPLATE, row * GRID_COLS + col + 1);
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Không tìm thấy ảnh: " + imagePath);
            applyPlaceholderStyle(imageView);
        }
    }

    private void applyPlaceholderStyle(ImageView imageView) {
        imageView.setStyle(String.format(
            "-fx-background-color: %s;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: %dpx;" +
            "-fx-border-radius: %dpx;" +
            "-fx-background-radius: %dpx;",
            Styles.PLACEHOLDER_BACKGROUND,
            Styles.PLACEHOLDER_BORDER,
            Styles.BORDER_WIDTH,
            Styles.BORDER_RADIUS,
            Styles.BORDER_RADIUS
        ));
    }

    private void updatePreviewInfo(int row, int col) {
        previewInfoBox.getChildren().clear();
        
        // Tạo các label cho thông tin sản phẩm
        Label titleLabel = createLabel(ProductData.PRODUCTS[row * GRID_COLS + col][0], 
            Font.font("System", FontWeight.BOLD, Styles.PREVIEW_TITLE_FONT_SIZE));
        titleLabel.setWrapText(true);
        
        Label descLabel = createLabel(ProductData.PRODUCTS[row * GRID_COLS + col][1], 
            Font.font("System", Styles.PREVIEW_DESC_FONT_SIZE));
        descLabel.setWrapText(true);
        
        Label brandLabel = createLabel("Hãng: " + ProductData.PRODUCTS[row * GRID_COLS + col][2], 
            Font.font("System", Styles.PREVIEW_BRAND_FONT_SIZE));
        
        Label priceLabel = createLabel(ProductData.PRODUCTS[row * GRID_COLS + col][3], 
            Font.font("System", FontWeight.BOLD, Styles.PREVIEW_PRICE_FONT_SIZE));
        priceLabel.setTextFill(Color.web(Styles.PRICE_COLOR));
        
        // Thêm các label vào preview info box
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