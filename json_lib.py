class JSONParseException(Exception):
	pass

'''
Shortcut for raising error
'''
def err(message: str = "Error while parsing JSON!"):
	raise JSONParseException(message)

'''
Return's true if given string is considered as number
'''
def is_number(string: str) -> bool:

	for c in string:
		if c not in "0123456789.-":
			return False

	return True

'''
Get data type of value, opposite of to_string(json) function
'''
def get_value(value: str):
	
	if value.startswith('"') and value.endswith('"'):

		return value[1:-1]

	elif value == "null":
		return None

	elif value == "true" or value == "false":
		return True if value == "true" else False

	elif is_number(value):

		return float(value) if "." in value else int(value)

	else:

		err("Invalid value! '" + str(value) + "'")

'''
Parse list without the first bracket
'''
def parse_dict(string: str) -> dict:

	dictonary = {}

	writing_key = True
	in_quotes = False

	key = ""
	value = ""

	last_char = ""

	nested = {
		"writing" : False
	}

	for c in string:

		if in_quotes:
			
			if c == '"' and last_char != "\\": # escape string 
				in_quotes = False
				
				if writing_key:
					key = value
					value = ""

				else:

					value = f'"{value}"'

			elif c != "\\":
				value += c

		elif nested["writing"]:
			
			if c == '"' and last_char != "\\":
				nested["in_quotes"] = not nested["in_quotes"]

			elif c == nested["open_bracket"] and not nested["in_quotes"]:
				nested["brackets"] += 1

			elif c == nested["close_bracket"] and not nested["in_quotes"]:
				nested["brackets"] -= 1

				if nested["brackets"] == 0:

					nested["text"] += nested["close_bracket"]

					dictonary[key] = nested["func"](nested["text"])

					value = ""
					key = ""

					nested["writing"] = False

			nested["text"] += c

		elif c == "{" or c == "[":
			nested["writing"] = True
			nested["open_bracket"] = c
			nested["text"] = ""
			nested["brackets"] = 1
			nested["in_quotes"] = False

			if c == "{":
				nested["close_bracket"] = "}"
				nested["func"] = parse_dict

			else:
				nested["close_bracket"] = "]"
				nested["func"] = parse_list

		elif c == "}":
			if key != "":
				dictonary[key] = get_value(value)

			return dictonary

		elif c == '"':
			in_quotes = True
			value = ""

		elif c == ":":
			writing_key = False

		elif c == ",":
			writing_key = True

			if value == "":
				continue

			dictonary[key] = get_value(value)

		elif c in "\n \t\r":
			pass

		else:

			if writing_key:
				err()

			value += c

		last_char = c

	err()

'''
Parse list without the first bracket
'''
def parse_list(string: str) -> list:
	
	nested = {
		"writing" : False
	}

	LIST = []
	value = ""

	in_quotes = False

	last_char = ""

	for c in string:	

		if in_quotes:

			if c == '"' and last_char != "\\":
				in_quotes = False

				value += '"'

			elif c != "\\":
				value += c

		elif nested["writing"]:

			if c == '"' and last_char != "\\":
				nested["in_quotes"] = not nested["in_quotes"]

			elif c == nested["open_bracket"] and not nested["in_quotes"]:
				nested["brackets"] += 1

			elif c == nested["close_bracket"] and not nested["in_quotes"]:
				nested["brackets"] -= 1

				if nested["brackets"] == 0:

					nested["text"] += nested["close_bracket"]

					LIST.append(nested["func"](nested["text"]))

					value = ""
					nested["writing"] = False

			nested["text"] += c

		elif c == "[" or c == "{":
			nested["writing"] = True
			nested["open_bracket"] = c
			nested["text"] = ""
			nested["brackets"] = 1
			nested["in_quotes"] = False

			if c == "{":
				nested["close_bracket"] = "}"
				nested["func"] = parse_dict

			else:
				nested["close_bracket"] = "]"
				nested["func"] = parse_list

		elif c == '"':
			in_quotes = True

			value += '"'

		elif c == "]":
			if value != "":

				LIST.append(get_value(value))

			return LIST

		elif c == ",":

			if value == "":
				continue
			
			LIST.append(get_value(value))

			value = ""

		elif c in "\t \n\r":
			pass

		else:
			value += c

		last_char = c

	print(LIST)

	err()

'''
Parse json to dict / list
'''
def parse(string: str) -> (dict, list):

	for i, c in enumerate(string):

		if c == "[":
			return parse_list(string[i+1:])

		elif c == "{":
			return parse_dict(string[i+1:])

	err()

'''
Converts json to string
'''
def to_string(json) -> str:

	if json == None:
		return "null"

	elif type(json) == str:
		return '"' + json.replace('"', '\\"') + '"'

	elif type(json) == dict:
		return dict_to_string(json)

	elif type(json) == list:
		return list_to_string(json)

	return str(json)

'''
Converts dict to string
'''
def dict_to_string(dictonary: dict) -> str:

	string = "{"

	for i, key in enumerate(dictonary):
		value = dictonary[key]

		string += f'"{key}": {to_string(value)}'

		if i != len(dictonary) - 1:
			string += ", "

	return string + "}"

'''
Converts list to string
'''
def list_to_string(LIST: list) -> str:

	string = "["

	for i, value in enumerate(LIST):

		string += to_string(value)

		if i != len(LIST) -1:
			string += ", "

	return string + "]"


'''
Examples
'''

print(parse('''
	{
		"text" : "\\"Hello World\\"",
		"boolean" : true,	
		"nested_dict" : {
			"nested_list" : [
				{
					"number" : 1,
					"number_" : 1.23,
					"null" : null
				},
				"123",
				"1234"
			]
		}
	}
'''))

print(to_string({
		'text': '"Hello World"',
		'boolean': True,
		'nested_dict': {
			'nested_list': [
				{
					'number': 1,
					'number_': 1.23,
					'null': None
				}
			]
		}
	}))
