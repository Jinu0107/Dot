package board;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MainBoard {
	private GraphicsContext gc;
	private int size = 30; // 블럭의 가로세로 사이즈
	private double color = 0.1; // 색상간의 간격
	private Color[][] board;
	private Color c1;// 키보드 1번
	private Color c2;// 키보드 2번
	private Color c3;// 키보드 3번
	private Color c4;// 키보드 4번
	private Color c5;// 키보드 5번
	private Color temp;// 현재 색
	private Color filltemp; // 채우기 임시색
	private Color spuittemp; // 스포이트 임시색

	public MainBoard(GraphicsContext gc) {
		this.gc = gc;
		board = new Color[32][32];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = Color.WHITE;
			}
		}
	}

	public void getColor1(Color c1) {
		// 키보드 1번을 눌렀을때 실행되는 메서드
		this.c1 = c1;
		checkkey(c1);
	}

	public void getColor2(Color c2) {
		// 키보드 2번을 눌렀을때 실행되는 메서드
		this.c2 = c2;
		checkkey(c2);
	}

	public void getColor3(Color c3) {
		// 키보드 3번을 눌렀을때 실행되는 메서드
		this.c3 = c3;
		checkkey(c3);
	}

	public void getColor4(Color c4) {
		// 키보드 4번을 눌렀을때 실행되는 메서드
		this.c4 = c4;
		checkkey(c4);
	}

	public void getColor5(Color c5) {
		// 키보드 5번을 눌렀을때 실행되는 메서드
		this.c5 = c5;
		checkkey(c5);
	}

	public void getColor6() {
		// 키보드 6번을 눌렀을때 실행되는 메서드
		// 스포이트임
		checkkey(spuittemp);
	}

	public void checkkey(Color c) {
		// 현재의 색을 정하는 temp 값에 키보드 클릭에 따라 받아온 color 값을 넣어줌
		temp = c;
	}

	public void restart() {
		// 초기화 버튼을 눌렀을때 실행되는 메서드
		// board의 모든값을 white 로 변경
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = Color.WHITE;
			}
		}
		render();
	}

	public void render() {
		// 제일 중요함, 캔버스에 변화가 생겼을때 이 메서드를 실행시켜야 그 변화가 실제로 보임
		// 캔버스에 색 입히는거
		gc.setStroke(Color.rgb(240, 240, 240));
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {
				int x = +(size) * j;
				int y = +(size) * i;

				gc.setFill(Color.rgb(86, 98, 112));
				gc.fillRect(x, y, size, size);

				gc.setFill(board[i][j]);
				gc.fillRect(x + color, y + color, size - 2 * color, size - 2 * color);

			}
		}
	}

	public void clickHandle(MouseEvent e) {
		// 얇게 버튼을 클릭 후 캔버스에 클릭을 했을때 실행되는 메서드
		double mx = e.getX();
		double my = e.getY();
		int x = (int) (mx / size);
		int y = (int) (my / size);
		if (y >= 32 && x >= 32) {
			return;
		}

		MouseButton btn = e.getButton();
		if (btn == MouseButton.SECONDARY) {
			// 우클
			rightClick(y, x);

		} else if (btn == MouseButton.PRIMARY) {
			// 좌클
			leftClick(y, x);
		}
		render();
	}

	public void clickHandle2(MouseEvent e) {
		// 굵게 버튼을 클릭후 캔버스에 클릭을 했을때 실행되는 메서드
		double mx = e.getX();
		double my = e.getY();
		int x = (int) (mx / size);
		int y = (int) (my / size);
		if (y >= 32 && x >= 32) {
			return;
		}

		MouseButton btn = e.getButton();
		if (btn == MouseButton.SECONDARY) {
			// 우클릭
			rightClick(y, x);// 지우개로 보냄
			render();

		} else if (btn == MouseButton.PRIMARY) {
			// 좌클릭
			leftClick2(y, x);
		}

	}

	private void leftClick2(int y, int x) {
		// clickHandle2 에서 좌클릭을 하였을때 실행되는 메소드 (한 번 그을때 9칸씩)
		// 굵은 펜으로그렸을때 선긋기
		if (temp != null) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (x + j < 0 || x + j >= 32 || y + i < 0 || y + i >= 32) {

					} else {
						board[y + i][x + j] = temp;
						render();
					}
				}
			}
		}
	}

	private void leftClick(int y, int x) {
		// 얇은 펜으로 그렸을때 선긋기
		if (temp != null) {
			if (x < 0 || x >= 32 || y < 0 || y >= 32) {

			} else {
				board[y][x] = temp;
				render();
			}
		}
	}

	private void rightClick(int y, int x) {
		// 우클릭 지우개(굵게는 얇게는 이리로 온다)
		board[y][x] = Color.WHITE;
	}

	public void checkcolor(GraphicsContext colorgc) {
		// 색 미리보기
		colorgc.setFill(temp);
		colorgc.fillRect(0, 0, 50, 50);

	}

	public void clickfill(MouseEvent e) {
		// 채우기 버튼을 선택하고 캔버스를 클릭했을때 실행되는 메서드
		double mx = e.getX();
		double my = e.getY();
		int x = (int) (mx / size);
		int y = (int) (my / size);
		if (y >= 32 && x >= 32) {
			return;
		}
		MouseButton btn = e.getButton();
		if (btn == MouseButton.PRIMARY) {
			// 좌클릭
			fill(y, x);// fill 메소드로 y,x 값을 보내줌
			render();
		}
	}

	public void fill(int y, int x) {
		// clickfill 에서 좌클릭을 했을때 실행되는 메서드
		filltemp = board[y][x];
		board[y][x] = temp;

		if (filltemp != temp) {

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (x + j < 0 || x + j >= 32 || y + i < 0 || y + i >= 32) {

					} else if (board[y + i][x + j] == filltemp) {
						if ((i != -1 || j != -1) && (i != -1 || j != 1) && (i != 1 || j != -1) && (i != 1 || j != 1)) {
							fill(y + i, x + j);
						}
					} else if (board[y + i][x + j] != filltemp) {
						board[y][x] = temp;
					}
				}
			}
		}
	}

	public void clickspuit(MouseEvent e, GraphicsContext colorgc, GraphicsContext spuitgc) {
		// 스포이트를 선택하고 캔버스를 클릭했을 때 실행되는 메서드
		double mx = e.getX();
		double my = e.getY();
		int x = (int) (mx / size);
		int y = (int) (my / size);
		if (y >= 32 && x >= 32) {
			return;
		}
		MouseButton btn = e.getButton();
		if (btn == MouseButton.PRIMARY) {
			// 좌클릭을 했다
			spuittemp = board[y][x];
			temp = spuittemp;
			// 현재 스포이트 색 표시
			spuitgc.setFill(spuittemp);
			spuitgc.fillRect(0, 0, 50, 50);
			// 현재 색 표시
			colorgc.setFill(temp);
			colorgc.fillRect(0, 0, 50, 50);
		}

	}

}
