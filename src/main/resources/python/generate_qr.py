from amzqr import QRCode
import sys

def generate_qr(data, output_path):
	"""
    amzqr를 사용하여 스타일 QR 코드 생성
    :param data: QR 코드에 저장할 데이터 (예: URL)
    :param output_path: 저장할 파일 경로
    """
	qr = QRCode()
	qr.run(
		words=data,  # QR 코드에 포함할 데이터 (URL 등)
		version=5,  # QR 코드 버전 (1~40)
		level='H',  # 오류 수정 수준 (L, M, Q, H)
		picture="cat.png",  # 배경 이미지 (동물 모양 가능)
		colorized=True,  # 색상 적용 여부
		contrast=1.0,  # 명암 조절 (1.0 = 기본값)
		brightness=1.0,  # 밝기 조절 (1.0 = 기본값)
		save_name=output_path,  # 저장 파일 경로
	)
	print(f"QR 코드가 생성되었습니다: {output_path}")

if __name__ == "__main__":
	url = sys.argv[1]  # 첫 번째 인자로 URL 받기
	output_file = sys.argv[2]  # 두 번째 인자로 저장할 파일명 받기
	generate_qr(url, output_file)
