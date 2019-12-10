package views;

import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;

import board.MainBoard;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Canvas maincanvas;// 메인 캔버스
	@FXML
	private Canvas minimap;// 미리보기 화면
	@FXML
	private Canvas checkcolor; // 현재 색
	@FXML
	private Canvas spuitcanvas; // 스포이트 색

	private MainBoard dotboard;// 메인 판

	@FXML
	private ColorPicker cp1;// 키보드 1번
	@FXML
	private ColorPicker cp2;// 2번
	@FXML
	private ColorPicker cp3;// 3번
	@FXML
	private ColorPicker cp4;// 4번
	@FXML
	private ColorPicker cp5;// 5번

	private Stage ps; // 프라이머리 스테이지

	private GraphicsContext gc;// 메인 캔버스 그림그리는거

	private GraphicsContext minigc;// 미리보기 그림그리는거

	private GraphicsContext colorgc;// 현재색 그림 그리는거

	private GraphicsContext spuitgc;// 스포이트 그림 그리는거

	private Color c1;// 키보드 1번의 컬러값
	private Color c2;// 키보드 2번의 컬러값
	private Color c3;// 키보드 3번의 컬러값
	private Color c4;// 키보드 4번의 컬러값
	private Color c5;// 키보드 5번의 컬러값

	private int cnt; // 컬러 초기화를 위한 카운트 값

	private int pensize = 2; // 펜 크기

	@FXML
	private Button bigbtn; // 펜 사이즈 크게
	@FXML
	private Button smallbtn;// 펜 사이즈 작게

	// 펜 모드
	@FXML
	private Button pen;// 펜
	@FXML
	private Button spuit;// 스포이트
	@FXML
	private Button fill;// 채우기

	private int penmod;// 펜 모드, 0일땐 펜, 1일땐 채우기, 2일땐 스포이트

	private int clickcnt; // 클릭핸들 카운트값

	private int pencnt; // 펜 카운트값

	private int firstcnt;

	@FXML
	private void initialize() {
		// 프로그램을 처음 실행시켰을때 한 번 실행되는 메서드
		gc = maincanvas.getGraphicsContext2D();
		dotboard = new MainBoard(gc);
		dotboard.render();

		minigc = minimap.getGraphicsContext2D();
		colorgc = checkcolor.getGraphicsContext2D();
		spuitgc = spuitcanvas.getGraphicsContext2D();

		// 처음에는 모든 버튼들을 비활성화 시켜준다
		bigbtn.setDisable(true);
		smallbtn.setDisable(true);
		pen.setDisable(true);
		fill.setDisable(true);
		spuit.setDisable(true);
		clickcnt = 0;
		pencnt = 0;
		firstcnt = 0;

		copyToSmall();
	}

	public void penclick() {
		// 펜클릭
		clickcnt = 1;
		penmod = 0;

		pen.setDisable(true);
		fill.setDisable(false);
		spuit.setDisable(false);
		smallbtn.setDisable(false);
		bigbtn.setDisable(false);
		if(pensize == 0 || pensize == 1) {
			smallbtn.setDisable(true);
		}
	}

	public void fillclick() {
		// 채우기 클릭
		clickcnt = 1;
		penmod = 1;
		pensize = 0;

		pen.setDisable(false);
		fill.setDisable(true);
		spuit.setDisable(false);
		bigbtn.setDisable(true);
		smallbtn.setDisable(true);
	}

	public void spuitclick() {
		// 스포이트 클릭
		clickcnt = 1;
		penmod = 2;
		pensize = 0;

		pen.setDisable(false);
		fill.setDisable(false);
		spuit.setDisable(true);
		bigbtn.setDisable(true);
		smallbtn.setDisable(true);
	}

	@FXML
	public void resetButton() {
		// 초기화 버튼을 클릭
		dotboard.restart();
		copyToSmall();
	}

	public void bigpen() {
		// 크게 클릭
		pencnt = 1;
		pensize = 1;

		bigbtn.setDisable(true);
		smallbtn.setDisable(false);
	}

	public void small() {
		// 작게 클릭
		pencnt = 1;
		pensize = 0;

		bigbtn.setDisable(false);
		smallbtn.setDisable(true);
	}

	public void clickHandle(MouseEvent e) {
		// 마우스 클릭,드래그
		if (clickcnt == 0) {
		} else {
			if (pencnt != 0) {
				if (pensize == 0) {
					// 펜 작게
					if (penmod == 0) {
						// 일반 펜
						dotboard.clickHandle(e);
					} else if (penmod == 1) {
						// 채우기
						dotboard.clickfill(e);
					} else {
						// 스포이트
						dotboard.clickspuit(e, colorgc, spuitgc);

					}

				} else {
					// 펜 크게
					dotboard.clickHandle2(e);
				}
				copyToSmall();
			}
		}

	}

	public void getColor1() {
		// 키보드 1번
		c1 = cp1.getValue();
		if (cnt == 1) {
			dotboard.getColor1(c1);
			dotboard.checkcolor(colorgc);
		}
	}

	public void getColor2() {
		// 키보드 2번
		c2 = cp2.getValue();
		if (cnt == 2) {
			dotboard.getColor2(c2);
			dotboard.checkcolor(colorgc);
		}
	}

	public void getColor3() {
		// 키보드 3번
		c3 = cp3.getValue();
		if (cnt == 3) {
			dotboard.getColor3(c3);
			dotboard.checkcolor(colorgc);
		}
	}

	public void getColor4() {
		// 키보드 4번
		c4 = cp4.getValue();
		if (cnt == 4) {
			dotboard.getColor4(c4);
			dotboard.checkcolor(colorgc);
		}
	}

	public void getColor5() {
		// 키보드 5번
		c5 = cp5.getValue();
		if (cnt == 5) {
			dotboard.getColor5(c5);
			dotboard.checkcolor(colorgc);
		}
	}

	public void keyHandle(KeyEvent e) {
		// 키보드 입력에 따라 컬러값을 MainBoard 로 보내주는 메서드
		if (e.getCode() == KeyCode.DIGIT1) {
			// 키보드 1번
			dotboard.getColor1(c1);
			cnt = 1;
			if (c1 != null) {
				if (firstcnt == 0) {
					firstcnt = 1;
					pen.setDisable(false);
				}
			}
		} else if (e.getCode() == KeyCode.DIGIT2) {
			// 키보드 2번
			dotboard.getColor2(c2);
			cnt = 2;
			if (c2 != null) {
				if (firstcnt == 0) {
					firstcnt = 1;
					pen.setDisable(false);
				}
			}
		} else if (e.getCode() == KeyCode.DIGIT3) {
			// 키보드 3번
			dotboard.getColor3(c3);
			cnt = 3;
			if (c3 != null) {
				if (firstcnt == 0) {
					firstcnt = 1;
					pen.setDisable(false);
				}
			}
		} else if (e.getCode() == KeyCode.DIGIT4) {
			// 키보드 4번
			dotboard.getColor4(c4);
			cnt = 4;
			if (c4 != null) {
				if (firstcnt == 0) {
					firstcnt = 1;
					pen.setDisable(false);
				}
			}
		} else if (e.getCode() == KeyCode.DIGIT5) {
			// 키보드 5번
			dotboard.getColor5(c5);
			cnt = 5;
			if (c5 != null) {
				if (firstcnt == 0) {
					firstcnt = 1;
					pen.setDisable(false);
				}
			}
		} else if (e.getCode() == KeyCode.DIGIT6) {
			// 키보드 6번
			// 스포이트
			dotboard.getColor6();
			cnt = 6;

		}
		dotboard.checkcolor(colorgc);

	}

	public void copyToSmall() {
		// 미리보기
		// 메인 캔버스에서 변화가 생길때 마다 실행시켜 우측에 작은 캔버스에 그 이미지를 그려줌
		WritableImage wImage = new WritableImage((int) maincanvas.getWidth(), (int) maincanvas.getHeight());
		maincanvas.snapshot(null, wImage);

		minigc.drawImage(wImage, 0, 0, minimap.getWidth(), minimap.getHeight());

	}

	public void exportToPng() {
		// 내보내기 버튼을 클릭했을때 실행되는 메서드

		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		fc.getExtensionFilters().add(extFilter);

		File file = fc.showSaveDialog(ps);

		if (file != null) {
			try {
				WritableImage wImage = new WritableImage((int) maincanvas.getWidth(), (int) maincanvas.getHeight());
				maincanvas.snapshot(null, wImage);

				RenderedImage renderedImage = SwingFXUtils.fromFXImage(wImage, null);
				ImageIO.write(renderedImage, "png", file);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("파일 저장 중 오류 발생");
			}
		}

	}

	public void setStage(Stage pr) {
		this.ps = pr;
	}
}
